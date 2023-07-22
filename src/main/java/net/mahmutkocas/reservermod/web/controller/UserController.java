package net.mahmutkocas.reservermod.web.controller;

import net.mahmutkocas.reservermod.web.dao.UserDAO;
import net.mahmutkocas.reservermod.web.dto.TokenDTO;
import net.mahmutkocas.reservermod.web.dto.UserDTO;
import net.mahmutkocas.reservermod.web.dto.UserLoginDTO;
import net.mahmutkocas.reservermod.web.mapper.UserMapper;
import net.mahmutkocas.reservermod.web.repository.UserRepository;
import net.mahmutkocas.reservermod.web.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        boolean success = service.register(UserMapper.toDAO(userDTO));
        return success ?
                new ResponseEntity<String>("Register successful!", HttpStatus.ACCEPTED)
                :
                new ResponseEntity<String>("Register failed!", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userDTO) {
        TokenDTO tokenDTO = service.login(UserMapper.toDAO(userDTO));
        if(tokenDTO == null) {
            return new ResponseEntity<String>("Login failed!", HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(tokenDTO);
    }

}
