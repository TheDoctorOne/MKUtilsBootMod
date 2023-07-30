package net.mahmutkocas.mkutils.server;

import net.mahmutkocas.mkutils.server.mc.CrateCommandHandler;
import net.mahmutkocas.mkutils.server.web.service.GeneralService;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.springframework.context.ConfigurableApplicationContext;

@SideOnly(Side.SERVER)
public class ServerGlobals {

    public static ConfigurableApplicationContext WEB;

    public static GeneralService WEBSERVICE;

    public static CrateCommandHandler crateCommandHandler;

    public static void initializeGlobals(ConfigurableApplicationContext web) {
        WEB = web;
        WEBSERVICE = web.getBean(GeneralService.class);
        crateCommandHandler = new CrateCommandHandler();
    }
}
