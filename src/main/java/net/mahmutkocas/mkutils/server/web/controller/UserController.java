package net.mahmutkocas.mkutils.server.web.controller;

import lombok.RequiredArgsConstructor;
import net.mahmutkocas.mkutils.common.dto.TokenDTO;
import net.mahmutkocas.mkutils.common.dto.UserDTO;
import net.mahmutkocas.mkutils.common.dto.UserLoginDTO;
import net.mahmutkocas.mkutils.server.web.mapper.UserMapper;
import net.mahmutkocas.mkutils.server.web.service.GeneralService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final GeneralService service;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        boolean success = service.register(UserMapper.toDAO(userDTO));
        return success ?
                new ResponseEntity<>("Register successful!", HttpStatus.ACCEPTED)
                :
                new ResponseEntity<>("User exists!", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UserLoginDTO userDTO) {
        TokenDTO tokenDTO = service.login(UserMapper.toDAO(userDTO));
        if(tokenDTO == null) {
            return new ResponseEntity<>(new TokenDTO("Login failed!"), HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(tokenDTO);
    }

}
