package net.mahmutkocas.reservermod;

import net.mahmutkocas.reservermod.client.events.ClientUserEvents;
import net.mahmutkocas.reservermod.client.events.ScreenEvents;
import net.mahmutkocas.reservermod.server.events.ServerUserEvents;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventHandle {

    @SideOnly(Side.CLIENT)
    private static void clientRegister() {
        MinecraftForge.EVENT_BUS.register(new ScreenEvents(Minecraft.getMinecraft()));
        MinecraftForge.EVENT_BUS.register(new ClientUserEvents());
    }

    @SideOnly(Side.SERVER)
    private static void serverRegister() {
        MinecraftForge.EVENT_BUS.register(ServerUserEvents.INSTANCE);
    }

    public static void register() {
        if(AppGlobals.SIDE.isClient()) {
            clientRegister();
        } else {
            serverRegister();
        }
    }
}
