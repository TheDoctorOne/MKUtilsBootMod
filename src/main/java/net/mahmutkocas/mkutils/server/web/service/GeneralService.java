package net.mahmutkocas.mkutils.server.web.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.mahmutkocas.mkutils.common.dto.CrateDTO;
import net.mahmutkocas.mkutils.server.web.dao.CrateContentDAO;
import net.mahmutkocas.mkutils.server.web.dao.UserCrateDAO;
import net.mahmutkocas.mkutils.server.web.dao.UserDAO;
import net.mahmutkocas.mkutils.common.dto.TokenDTO;
import net.mahmutkocas.mkutils.server.web.repository.CrateRepository;
import net.mahmutkocas.mkutils.server.web.repository.UserCrateRepository;
import net.mahmutkocas.mkutils.server.web.repository.UserRepository;
import net.minecraftforge.fml.server.FMLServerHandler;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Getter
@Log4j2
public class GeneralService {

    private final UserRepository userRepository;
    private final CrateRepository crateRepository;
    private final UserCrateRepository userCrateRepository;
    private final Random random = new Random();

    public String getUserByToken(TokenDTO tokenDTO) {
        if(tokenDTO == null || tokenDTO.getToken() == null) {
            return null;
        }

        String tokenRaw = tokenDTO.getToken();
        String[] splt = tokenRaw.split(";");

        if(splt.length != 2) {
            return null;
        }

        String userId = splt[0];

        try {
            Optional<UserDAO> userOpt = userRepository.findById(Long.parseLong(userId));
            if(!userOpt.isPresent()) {
                return null;
            }
            if(userOpt.get().getTokenExpDate().isAfter(LocalDateTime.now())) {
                return userOpt.get().getToken().equals(tokenRaw) ? userOpt.get().getUsername() : null;
            }
        } catch (Exception e) {
            log.error("Token exception", e);
        }
        return null;
    }

    public CrateContentDAO openCrate(String player, List<UserCrateDAO> userCrates, CrateDTO target) {
        UserCrateDAO crate = null;
        for (UserCrateDAO userCrate : userCrates) {
            if(userCrate.isClaimed()) {
                continue;
            }
            if(userCrate.getCrateDAO().getId().equals(target.getId())) {
                crate = userCrate;
                break;
            }
        }

        if(crate != null) {
            UserCrateDAO userCrateDAO = userCrateRepository.findById(crate.getId()).orElse(null);
            if(userCrateDAO == null) {
                log.error("User({}) crate does not exists!", player);
                return null;
            }
            userCrateDAO.setClaimed(true);
            userCrateRepository.save(userCrateDAO);
            Set<CrateContentDAO> crateContents = userCrateDAO.getCrateDAO().getCrateContents();
            int total = 0;
            for(CrateContentDAO crateContent : crateContents) {
                total += crateContent.getChance();
            }

            int rand = random.nextInt();
            if(rand < 0) rand *= -1;
            rand %= total;

            total = 0;
            CrateContentDAO prev = null;
            for(CrateContentDAO crateContent : crateContents) {
                total += crateContent.getChance();
                if(rand < total && prev != null) {
                    openCrate(player, userCrateDAO, prev);
                    return prev;
                }
                prev = crateContent;
            }
        }
        return null;
    }

    private static void openCrate(String player, UserCrateDAO userCrateDAO, CrateContentDAO prev) {
        String[] cmds = prev.getCommand().split("%c%");
        for(String cmd : cmds) {
            String targetCmd = cmd.replaceAll("%p%", player);
            log.info("{} opened a crate {}:{}! And WON {}! Command: {}", player, userCrateDAO.getId(), userCrateDAO.getCrateDAO().getName(), prev.getName(), targetCmd);
            FMLServerHandler.instance().getServer().commandManager.executeCommand(FMLServerHandler.instance().getServer(), targetCmd);
            userCrateDAO.setClaimed(true);
        }
    }

    public UserDAO getUserByName(String name) {
        return userRepository.findByUsername(name.toLowerCase(Locale.ENGLISH)).orElse(null);
    }

    public TokenDTO login(UserDAO req) {
        if(req.getUsername().isEmpty() || req.getUsername().length() > 16 || req.getPassword().isEmpty() || req.getUsername().contains(";")) {
            return null;
        }

        Optional<UserDAO> oUserDAO = userRepository.findByUsername(req.getUsername());
        if(!oUserDAO.isPresent()) {
            oUserDAO = Optional.of(userRepository.save(req));
        }

        UserDAO userDAO = oUserDAO.get();
        if(!userDAO.getPassword().equals(req.getPassword())) {
            return null;
        }

        userDAO.setToken(userDAO.getId() + ";" + UUID.randomUUID());
        userDAO.setTokenExpDate(LocalDateTime.now().plusHours(1));
        userRepository.save(userDAO);

        return new TokenDTO(userDAO.getToken());
    }

    public boolean register(UserDAO req) {
        if(req.getUsername().isEmpty() || req.getUsername().length() > 16 || req.getPassword().isEmpty() || req.getUsername().contains(";")) {
            return false;
        }

        Optional<UserDAO> oUserDAO = userRepository.findByUsername(req.getUsername());
        if(oUserDAO.isPresent()) {
            return false;
        }

        userRepository.save(req);

        return true;
    }
}
