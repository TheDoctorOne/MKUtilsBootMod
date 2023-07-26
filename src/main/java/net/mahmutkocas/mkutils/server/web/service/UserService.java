package net.mahmutkocas.mkutils.server.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.mahmutkocas.mkutils.server.web.dao.UserDAO;
import net.mahmutkocas.mkutils.common.dto.TokenDTO;
import net.mahmutkocas.mkutils.server.web.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class UserService {

    private final UserRepository repository;

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
            Optional<UserDAO> userOpt = repository.findById(Long.parseLong(userId));
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

    public TokenDTO login(UserDAO req) {
        if(req.getUsername().isEmpty() || req.getUsername().length() > 16 || req.getPassword().isEmpty() || req.getUsername().contains(";")) {
            return null;
        }

        Optional<UserDAO> oUserDAO = repository.findByUsername(req.getUsername());
        if(!oUserDAO.isPresent()) {
            oUserDAO = Optional.of(repository.save(req));
        }

        UserDAO userDAO = oUserDAO.get();
        if(!userDAO.getPassword().equals(req.getPassword())) {
            return null;
        }

        userDAO.setToken(userDAO.getId() + ";" + UUID.randomUUID());
        userDAO.setTokenExpDate(LocalDateTime.now().plusHours(1));
        repository.save(userDAO);

        return new TokenDTO(userDAO.getToken());
    }

    public boolean register(UserDAO req) {
        if(req.getUsername().isEmpty() || req.getUsername().length() > 16 || req.getPassword().isEmpty() || req.getUsername().contains(";")) {
            return false;
        }

        Optional<UserDAO> oUserDAO = repository.findByUsername(req.getUsername());
        if(oUserDAO.isPresent()) {
            return false;
        }

        repository.save(req);

        return true;
    }
}
