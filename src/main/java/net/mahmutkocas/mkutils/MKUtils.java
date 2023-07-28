package net.mahmutkocas.mkutils;

import lombok.SneakyThrows;
import net.mahmutkocas.mkutils.client.ClientGlobals;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.server.FMLServerHandler;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

@Mod(modid = MKUtils.MODID, name = MKUtils.NAME, version = MKUtils.VERSION)
public class MKUtils
{
    public static final String MODID = "mkutils";
    public static final String NAME = "Mahmut Kocas Utility Mod";
    public static final String VERSION = "0.0.1";

    private static Logger logger;

    @EventHandler
    private void serverStarting(FMLServerAboutToStartEvent event) {
        event.getServer().isDedicatedServer();
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

}
