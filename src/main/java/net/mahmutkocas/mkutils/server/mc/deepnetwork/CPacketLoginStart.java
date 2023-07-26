package net.mahmutkocas.mkutils.server.mc.deepnetwork;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginServer;

import java.io.IOException;
import java.util.UUID;

public class CPacketLoginStart extends net.minecraft.network.login.client.CPacketLoginStart {
    private GameProfile profile;

    public CPacketLoginStart()
    {
    }

    public CPacketLoginStart(GameProfile profileIn)
    {
        this.profile = profileIn;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.profile = new GameProfile((UUID)null, buf.readString(100));
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeString(this.profile.getName());
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerLoginServer handler)
    {
        handler.processLoginStart(this);
    }

    public GameProfile getProfile()
    {
        return this.profile;
    }
}
