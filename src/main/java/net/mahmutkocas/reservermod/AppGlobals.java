package net.mahmutkocas.reservermod;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import net.mahmutkocas.reservermod.client.network.web.client.UserClient;
import net.mahmutkocas.reservermod.common.dto.TokenDTO;
import net.minecraftforge.fml.relauncher.Side;
import org.springframework.context.ConfigurableApplicationContext;

public class AppGlobals {

    public static Side SIDE;
    public static ConfigurableApplicationContext WEB;

    public static void setWEB(ConfigurableApplicationContext web) {
        WEB = web;
    }


}
