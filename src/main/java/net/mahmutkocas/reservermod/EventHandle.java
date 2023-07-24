package net.mahmutkocas.reservermod;

import net.mahmutkocas.reservermod.screen.ScreenHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public class EventHandle {

    private static void clientRegister() {
        MinecraftForge.EVENT_BUS.register(new ScreenHandler(Minecraft.getMinecraft()));
    }

    public static void register() {
        if(AppGlobals.SIDE.isClient()) {
            clientRegister();
        }
    }
}
