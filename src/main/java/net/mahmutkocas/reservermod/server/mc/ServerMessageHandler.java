package net.mahmutkocas.reservermod.server.mc;

import net.mahmutkocas.reservermod.common.MinecraftMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class ServerMessageHandler implements IMessageHandler<MinecraftMessage, IMessage> {
    @Override
    public IMessage onMessage(MinecraftMessage message, MessageContext ctx) {
        return null;
    }
}
