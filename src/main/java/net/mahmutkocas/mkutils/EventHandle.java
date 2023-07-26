package net.mahmutkocas.mkutils;

import net.mahmutkocas.mkutils.client.events.ClientUserEvents;
import net.mahmutkocas.mkutils.client.events.ScreenEvents;
import net.mahmutkocas.mkutils.client.network.mc.ClientMessageHandler;
import net.mahmutkocas.mkutils.common.MinecraftMessage;
import net.mahmutkocas.mkutils.server.events.ServerUserEvents;
import net.mahmutkocas.mkutils.server.mc.ServerMessageHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventHandle {

    @SideOnly(Side.CLIENT)
    private static void clientRegister() {
        MinecraftForge.EVENT_BUS.register(new ScreenEvents(Minecraft.getMinecraft()));
//        MinecraftForge.EVENT_BUS.register(new ClientUserEvents());
//        AppGlobals.NETWORK.registerMessage(ClientMessageHandler.class, MinecraftMessage.class, 0, Side.CLIENT);
    }

    @SideOnly(Side.SERVER)
    private static void serverRegister() {
//        MinecraftForge.EVENT_BUS.register(ServerUserEvents.INSTANCE);
//        AppGlobals.NETWORK.registerMessage(ServerMessageHandler.class, MinecraftMessage.class, 0, Side.SERVER);
    }

    public static void register() {
        if(AppGlobals.SIDE.isClient()) {
            clientRegister();
        } else {
            serverRegister();
        }
    }
}
