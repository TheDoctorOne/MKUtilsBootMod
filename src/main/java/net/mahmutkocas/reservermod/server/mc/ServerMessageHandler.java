package net.mahmutkocas.reservermod.server.mc;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import net.mahmutkocas.reservermod.common.MinecraftMessage;
import net.mahmutkocas.reservermod.common.dto.ModTokenDTO;
import net.mahmutkocas.reservermod.common.dto.TokenDTO;
import net.mahmutkocas.reservermod.server.ServerGlobals;
import net.mahmutkocas.reservermod.server.events.ServerUserEvents;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
@Log4j2
public class ServerMessageHandler implements IMessageHandler<MinecraftMessage, IMessage> {
    @Override
    public IMessage onMessage(MinecraftMessage message, MessageContext ctx) {
        try {
            ModTokenDTO dto = message.getMsg(ModTokenDTO.class);
            String token = dto.getToken();
            String username = ServerGlobals.USER_SERVICE.getUserByToken(new TokenDTO(token));
            if(username == null) {
                if(ctx.getServerHandler().player.hasDisconnected()) {
                   return null;
                }
                ctx.getServerHandler().player.connection.disconnect(new TextComponentString("Giriş Yapın!"));
                return null;
            }
            ServerUserEvents.INSTANCE.userConfirmed(username);
        } catch (JsonProcessingException e) {
            log.error(
                    "Message parse error! User: " + ctx.getServerHandler().player.getName() + " Message: " + message.getMsg()
                    , e);
        }

        return null;
    }
}
