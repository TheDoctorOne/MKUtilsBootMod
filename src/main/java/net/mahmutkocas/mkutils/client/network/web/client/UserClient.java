package net.mahmutkocas.mkutils.client.network.web.client;

import feign.Headers;
import feign.RequestLine;
import net.mahmutkocas.mkutils.common.dto.TokenDTO;
import net.mahmutkocas.mkutils.common.dto.UserDTO;
import net.mahmutkocas.mkutils.common.dto.UserLoginDTO;

@Headers({"Content-Type: application/json"})
public interface UserClient {

    @RequestLine("POST /user/register")
    String register(UserDTO userDTO);

    @RequestLine("POST /user/login")
    TokenDTO login(UserLoginDTO userDTO);
}
