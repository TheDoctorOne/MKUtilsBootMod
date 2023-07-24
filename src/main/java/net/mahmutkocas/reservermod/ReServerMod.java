package net.mahmutkocas.reservermod;

import net.mahmutkocas.reservermod.client.ClientGlobals;
import net.mahmutkocas.reservermod.server.ServerGlobals;
import net.mahmutkocas.reservermod.server.web.WebApplication;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ReServerMod.MODID, name = ReServerMod.NAME, version = ReServerMod.VERSION)
public class ReServerMod
{
    public static final String MODID = "reservermod";
    public static final String NAME = "Mahmut Kocas Server Mod";
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
            logger.info("User client started!");
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        EventHandle.register();
    }
}
