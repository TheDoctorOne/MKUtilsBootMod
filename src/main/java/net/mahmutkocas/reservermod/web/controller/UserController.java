package net.mahmutkocas.reservermod.web.controller;

import net.mahmutkocas.reservermod.web.dao.UserDAO;
import net.mahmutkocas.reservermod.web.dto.UserDTO;
import net.mahmutkocas.reservermod.web.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository repository;


    @RequestMapping("/register")
    public void register(@RequestBody UserDTO userDTO) {
        repository.save(UserDAO.builder()
                        .username(userDTO.getUsername())
                        .password(DigestUtils.sha256Hex(userDTO.getPassword()))
                        .mail(userDTO.getMail())
                        .build());
    }


}
