package net.mahmutkocas.reservermod.server;

import net.mahmutkocas.reservermod.server.events.ServerUserEvents;
import net.mahmutkocas.reservermod.server.web.service.UserService;
import org.springframework.context.ConfigurableApplicationContext;

public class ServerGlobals {

    public static ConfigurableApplicationContext WEB;

    public static UserService USER_SERVICE;

    public static void setWEB(ConfigurableApplicationContext web) {
        WEB = web;
        USER_SERVICE = web.getBean(UserService.class);
    }
}
