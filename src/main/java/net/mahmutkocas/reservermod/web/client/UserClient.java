package net.mahmutkocas.reservermod.web.client;

import feign.Headers;
import feign.RequestLine;
import net.mahmutkocas.reservermod.web.dto.TokenDTO;
import net.mahmutkocas.reservermod.web.dto.UserDTO;
import net.mahmutkocas.reservermod.web.dto.UserLoginDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user", url = "http://localhost:8080")
@Headers({"Content-Type: application/json"})
public interface UserClient {

    @PostMapping("/user/register")
    @RequestLine("POST /user/register")
    ResponseEntity<String> register(@RequestBody UserDTO userDTO);

    @PostMapping("/user/login")
    @RequestLine("POST /user/login")
    ResponseEntity<TokenDTO> login(@RequestBody UserLoginDTO userDTO);
}
