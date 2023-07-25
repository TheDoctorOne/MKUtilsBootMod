package net.mahmutkocas.mkutils.client.network.web.client;

import feign.Headers;
import feign.RequestLine;
import net.mahmutkocas.mkutils.common.dto.TokenDTO;
import net.mahmutkocas.mkutils.common.dto.UserDTO;
import net.mahmutkocas.mkutils.common.dto.UserLoginDTO;
import org.springframework.web.bind.annotation.RequestBody;

@Headers({"Content-Type: application/json"})
public interface UserClient {

    @RequestLine("POST /user/register")
    String register(@RequestBody UserDTO userDTO);

    @RequestLine("POST /user/login")
    TokenDTO login(@RequestBody UserLoginDTO userDTO);
}
