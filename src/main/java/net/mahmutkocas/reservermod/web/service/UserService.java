package net.mahmutkocas.reservermod.web.service;

import net.mahmutkocas.reservermod.web.dao.UserDAO;
import net.mahmutkocas.reservermod.web.dto.TokenDTO;
import net.mahmutkocas.reservermod.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository repository;


    public TokenDTO login(UserDAO req) {
        Optional<UserDAO> oUserDAO = repository.findByUsername(req.getUsername());
        if(!oUserDAO.isPresent()) {
            return null;
        }

        UserDAO userDAO = oUserDAO.get();
        if(!userDAO.getPassword().equals(req.getPassword())) {
            return null;
        }

        userDAO.setToken(UUID.randomUUID().toString());
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
