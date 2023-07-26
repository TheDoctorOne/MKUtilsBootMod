package net.mahmutkocas.mkutils.server.mc.deepnetwork;

import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.INetHandler;
import net.minecraft.network.login.INetHandlerLoginServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;

public class NetworkManager extends net.minecraft.network.NetworkManager {

    private final DedicatedServer server;

    public NetworkManager(EnumPacketDirection packetDirection, DedicatedServer server) {
        super(packetDirection);
        this.server = server;
    }

    @Override
    public void setNetHandler(INetHandler handler) {
        if(handler instanceof net.minecraft.server.network.NetHandlerLoginServer) {
            handler = new NetHandlerLoginServer(server, this);
        }
        super.setNetHandler(handler);
    }
}
