package net.mahmutkocas.mkutils.server.mc;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import net.mahmutkocas.mkutils.common.MinecraftMessage;
import net.mahmutkocas.mkutils.common.dto.ModTokenDTO;
import net.mahmutkocas.mkutils.common.dto.TokenDTO;
import net.mahmutkocas.mkutils.server.ServerGlobals;
import net.mahmutkocas.mkutils.server.events.ServerUserEvents;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Locale;

@SideOnly(Side.SERVER)
@Log4j2
public class ServerMessageHandler implements IMessageHandler<MinecraftMessage, IMessage> {
    @Override
    public IMessage onMessage(MinecraftMessage message, MessageContext ctx) {
        return null;
    }
}
