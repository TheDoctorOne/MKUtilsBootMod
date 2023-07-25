package net.mahmutkocas.mkutils.client;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import net.mahmutkocas.mkutils.client.network.web.client.UserClient;
import net.mahmutkocas.mkutils.common.dto.TokenDTO;

public class ClientGlobals {

    private static TokenDTO userToken;
    private static UserClient userClient;

    public static void runUserClient() {
        userClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(UserClient.class, "http://localhost:8080");
    }

    public static UserClient getUserClient() {
        return userClient;
    }

    public static void setUserToken(TokenDTO userToken) {
        ClientGlobals.userToken = userToken;
    }

    public static TokenDTO getUserToken() {
        return userToken;
    }
}
