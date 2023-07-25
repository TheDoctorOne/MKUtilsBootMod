package net.mahmutkocas.mkutils;

import net.mahmutkocas.mkutils.client.ClientGlobals;
import net.mahmutkocas.mkutils.server.ServerGlobals;
import net.mahmutkocas.mkutils.server.web.WebApplication;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = MKUtils.MODID, name = MKUtils.NAME, version = MKUtils.VERSION)
public class MKUtils
{
    public static final String MODID = "mkutils";
    public static final String NAME = "Mahmut Kocas Utility Mod";
    public static final String VERSION = "0.0.1";

    private static Logger logger;

    @EventHandler
    private void serverStarting(FMLServerStartingEvent event) {
        if(event.getServer().isDedicatedServer()) {
            ServerGlobals.setWEB(WebApplication.start(event.getServer()));

            logger.info("Web App Run!");
        }
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();

        AppGlobals.SIDE = event.getSide();
        if(AppGlobals.SIDE.isClient()) {
            ClientGlobals.runUserClient();
            ClientGlobals.readConfig();
            logger.info("User client started!");
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        EventHandle.register();
    }

    @EventHandler
    public void serverStop(FMLServerStoppingEvent event) {
        ServerGlobals.WEB.stop();
    }
}
