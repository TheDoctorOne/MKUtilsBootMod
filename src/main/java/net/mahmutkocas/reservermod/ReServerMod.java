package net.mahmutkocas.reservermod;

import net.mahmutkocas.reservermod.web.WebApplication;
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
            WebApplication.WebApp();
            logger.info("Web App Run!");
        }
    }


    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    }
}
