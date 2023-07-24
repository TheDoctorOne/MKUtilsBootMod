package net.mahmutkocas.reservermod.client.network.web.client;

import feign.Headers;
import feign.RequestLine;
import net.mahmutkocas.reservermod.common.dto.TokenDTO;
import net.mahmutkocas.reservermod.common.dto.UserDTO;
import net.mahmutkocas.reservermod.common.dto.UserLoginDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Headers({"Content-Type: application/json"})
public interface UserClient {

    @RequestLine("POST /user/register")
    ResponseEntity<String> register(@RequestBody UserDTO userDTO);

    @RequestLine("POST /user/login")
    ResponseEntity<TokenDTO> login(@RequestBody UserLoginDTO userDTO);
}
