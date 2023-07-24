package net.mahmutkocas.reservermod.server.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.mahmutkocas.reservermod.server.web.dao.UserDAO;
import net.mahmutkocas.reservermod.common.dto.TokenDTO;
import net.mahmutkocas.reservermod.server.web.repository.UserRepository;
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

    public boolean isTokenValid(TokenDTO tokenDTO) {
        if(tokenDTO == null || tokenDTO.getToken() == null) {
            return false;
        }

        String tokenRaw = tokenDTO.getToken();
        String[] splt = tokenRaw.split(";");

        if(splt.length != 2) {
            return false;
        }

        String userId = splt[0];
        String token = splt[1];

        try {
            Optional<UserDAO> userOpt = repository.findById(Long.parseLong(userId));
            return userOpt.filter(userDAO -> token.equals(userDAO.getToken())).isPresent();
        } catch (Exception e) {
            log.error("Token exception", e);
        }
        return false;
    }

    public TokenDTO login(UserDAO req) {
        Optional<UserDAO> oUserDAO = repository.findByUsername(req.getUsername());
        if(!oUserDAO.isPresent()) {
            return null;
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
        Optional<UserDAO> oUserDAO = repository.findByUsername(req.getUsername());
        if(oUserDAO.isPresent()) {
            return false;
        }

        repository.save(req);

        return true;
    }
}
