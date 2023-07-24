package net.mahmutkocas.reservermod.server.mc;

import com.fasterxml.jackson.core.JsonProcessingException;
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
public class ServerMessageHandler implements IMessageHandler<MinecraftMessage, IMessage> {
    @Override
    public IMessage onMessage(MinecraftMessage message, MessageContext ctx) {
        try {
            ModTokenDTO dto = message.getMsg(ModTokenDTO.class);
            String token = dto.getToken();
            String username = ServerGlobals.USER_SERVICE.getUserByToken(new TokenDTO(token));
            if(username == null) {
                ctx.getServerHandler().player.connection.disconnect(new TextComponentString("Giriş Yapın!"));
            }
            ServerUserEvents.INSTANCE.userConfirmed(username);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
