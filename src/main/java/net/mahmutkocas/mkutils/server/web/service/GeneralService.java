package net.mahmutkocas.mkutils.server.web.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.mahmutkocas.mkutils.common.dto.CrateDTO;
import net.mahmutkocas.mkutils.server.ServerGlobals;
import net.mahmutkocas.mkutils.server.web.dao.CrateContentDAO;
import net.mahmutkocas.mkutils.server.web.dao.CrateDAO;
import net.mahmutkocas.mkutils.server.web.dao.UserCrateDAO;
import net.mahmutkocas.mkutils.server.web.dao.UserDAO;
import net.mahmutkocas.mkutils.common.dto.TokenDTO;
import net.mahmutkocas.mkutils.server.web.dto.file.CrateFileDTO;
import net.mahmutkocas.mkutils.server.web.mapper.file.CrateContentFileMapper;
import net.mahmutkocas.mkutils.server.web.mapper.file.CrateFileMapper;
import net.mahmutkocas.mkutils.server.web.repository.CrateContentRepository;
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
    private final CrateContentRepository crateContentRepository;
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

    public boolean saveCrate(CrateFileDTO dto) {
        CrateDAO current = crateRepository.findByName(dto.getName()).orElse(null);
        if(current != null) {
            throw new IllegalArgumentException(current.getName() + " isimli kasa bulunmakta! Kasa adini degistiriniz.");
        }
        List<CrateContentDAO> contentDAOs = CrateContentFileMapper.toDAO(dto.getContents());
        crateContentRepository.saveAll(contentDAOs);

        CrateDAO dao = CrateFileMapper.toDAO(dto);
        dao.setCrateContents(new HashSet<>(contentDAOs));

        crateRepository.save(dao);
        return true;
    }

    public List<UserCrateDAO> getUserCrates(String username, boolean onlyValid) {
        UserDAO user = userRepository.findByUsername(username).orElse(null);
        if(user == null) {
            log.info("User does not exists {}", username);
            throw new IllegalArgumentException("Kullanici bulunamadi!");
        }

        List<UserCrateDAO> userCrate =  user.getCrates();

        if(onlyValid) {
            userCrate = userCrate.stream().filter(
                    crate -> !crate.isClaimed()
            ).collect(Collectors.toList());
        }

        return userCrate;
    }

    public List<CrateDAO> getCrates() {
        return crateRepository.findAll();
    }

    public void deleteUserCrate(String username, String crateName) {
        List<UserCrateDAO> userCrates = getUserCrates(username, true).stream()
                .filter(userCrateDAO -> userCrateDAO.getCrateDAO().getName().equals(crateName))
                .collect(Collectors.toList());

        if(userCrates.isEmpty()) {
            throw new IllegalArgumentException("Player: "+ username + " does not has the crate " + crateName);
        }

        userCrateRepository.delete(userCrates.get(0));
    }

    public void giveUserCrate(String username, String crateName) {
        UserDAO userDAO = userRepository
                .findByUsername(username.toLowerCase(Locale.ENGLISH)).orElse(null);

        if(userDAO == null) {
            throw new IllegalArgumentException("Kullanici bulunamadi!");
        }

        CrateDAO crateDAO = crateRepository.findByName(crateName).orElse(null);
        if(crateDAO == null) {
            throw new IllegalArgumentException("Kasa bulunamadi!");
        }
        UserCrateDAO userCrate = UserCrateDAO.builder()
                .crateDAO(crateDAO)
                .userDAO(userDAO)
                .claimed(false)
                .build();

        userCrateRepository.save(userCrate);
        userDAO.getCrates().add(userCrate);
        userRepository.save(userDAO);
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

        if(crate == null) {
            return null;
        }

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
        for(CrateContentDAO crateContent : crateContents) {
            total += crateContent.getChance();
            if(rand < total) {
                claimCrate(player, userCrateDAO, crateContent);
                return crateContent;
            }
        }

        return null;
    }

    private void claimCrate(String player, UserCrateDAO userCrateDAO, CrateContentDAO current) {
        String[] cmds = current.getCommand().split(";");
        for(String cmd : cmds) {
            if(cmd.trim().isEmpty()) {
                continue;
            }
            String targetCmd = cmd.trim().replaceAll("%p%", player);

            while(targetCmd.contains("%r%")) {
                String[] splt = targetCmd.split("%r%");
                int minInd = splt[1].indexOf("[min:");
                int maxInd = splt[1].indexOf("%max:");
                int closeInd= splt[1].indexOf("]");
                String minStr = splt[1].substring(minInd+5, maxInd);
                String maxStr = splt[1].substring(maxInd+5, closeInd);
                int min = Integer.parseInt(minStr);
                int max = Integer.parseInt(maxStr);
                int rand = random.nextInt();
                if(rand < 0) {
                    rand *= -1;
                }
                rand %= (max-min);
                rand += min;
                String target = "%r%\\[min:" + minStr + "%max:"+ maxStr + "]";
                targetCmd = targetCmd.replaceFirst(target, String.valueOf(rand));
            }

            log.info("{} opened a crate {}:{}! And WON {}! Command: {}", player, userCrateDAO.getId(), userCrateDAO.getCrateDAO().getName(), current.getName(), targetCmd);
            FMLServerHandler.instance().getServer().commandManager.executeCommand(ServerGlobals.crateCommandSender, targetCmd);
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
