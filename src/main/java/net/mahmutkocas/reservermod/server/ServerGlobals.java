package net.mahmutkocas.reservermod.server;

import org.springframework.context.ConfigurableApplicationContext;

public class ServerGlobals {

    public static ConfigurableApplicationContext WEB;

    public static void setWEB(ConfigurableApplicationContext web) {
        WEB = web;
    }
}
