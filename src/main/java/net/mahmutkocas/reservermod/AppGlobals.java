package net.mahmutkocas.reservermod;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import net.mahmutkocas.reservermod.web.client.UserClient;
import net.mahmutkocas.reservermod.web.dto.TokenDTO;
import net.minecraftforge.fml.relauncher.Side;
import org.springframework.context.ConfigurableApplicationContext;

public class AppGlobals {

    public static Side SIDE;
    public static ConfigurableApplicationContext WEB;
    private static UserClient userClient;
    public static TokenDTO userToken;

    public static void setWEB(ConfigurableApplicationContext web) {
        WEB = web;
    }

    public static void runUserClient() {
        userClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(UserClient.class, "http://localhost:8080");
    }

    public static UserClient getUserClient() {
        return userClient;
    }
}
