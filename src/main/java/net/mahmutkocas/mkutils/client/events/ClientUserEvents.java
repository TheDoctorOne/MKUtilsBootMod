package net.mahmutkocas.mkutils.client.events;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientUserEvents {

    @SubscribeEvent
    public void onServerConnection(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {

    }
}
