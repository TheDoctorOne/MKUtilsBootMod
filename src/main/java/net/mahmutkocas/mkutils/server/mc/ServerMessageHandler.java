package net.mahmutkocas.mkutils.server.mc;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import net.mahmutkocas.mkutils.common.MinecraftMessage;
import net.mahmutkocas.mkutils.common.dto.CrateContentDTO;
import net.mahmutkocas.mkutils.common.dto.CrateDTO;
import net.mahmutkocas.mkutils.common.dto.CrateDTOList;
import net.mahmutkocas.mkutils.server.ServerGlobals;
import net.mahmutkocas.mkutils.server.web.dao.CrateContentDAO;
import net.mahmutkocas.mkutils.server.web.dao.CrateDAO;
import net.mahmutkocas.mkutils.server.web.dao.UserCrateDAO;
import net.mahmutkocas.mkutils.server.web.dao.UserDAO;
import net.mahmutkocas.mkutils.server.web.mapper.CrateContentMapper;
import net.mahmutkocas.mkutils.server.web.mapper.CrateMapper;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.server.FMLServerHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SideOnly(Side.SERVER)
@Log4j2
public class ServerMessageHandler implements IMessageHandler<MinecraftMessage, IMessage> {
    @SneakyThrows
    @Override
    public IMessage onMessage(MinecraftMessage message, MessageContext ctx) {
        if(message == null || message.getMsg() == null) {
            return null;
        }

        String playerName = ctx.getServerHandler().player.getName();
        UserDAO userDAO = ServerGlobals.WEBSERVICE.getUserByName(playerName);
        if(userDAO == null) {
            log.fatal("USER DOES NOT EXISTS! BUT SENDS MESSAGES? USER: {} MESSAGE: {}", playerName, message);
            return null;
        }

        List<CrateDAO> crates = userDAO.getCrates()
                .stream().filter(crate -> !crate.isClaimed())
                .map(UserCrateDAO::getCrateDAO).collect(Collectors.toList());

        if(crates.isEmpty()) {
            log.info("Player: {}, No crate found but message is here: " + message, playerName);
            return new MinecraftMessage(
                    MinecraftMessage.MCMessageType.CRATE_LIST, new CrateDTOList(new ArrayList<>()));
        }

        switch (message.getType()) {
            case GET_CRATES:
                return new MinecraftMessage(
                        MinecraftMessage.MCMessageType.CRATE_LIST, new CrateDTOList(CrateMapper.toDTO(crates)) );
            case CRATE_OPEN:
                CrateContentDAO res = ServerGlobals.WEBSERVICE.openCrate(playerName, userDAO.getCrates(), message.getMsg(CrateDTO.class));
                String cmd = res.getCommand().replaceAll("%p%", playerName);
                FMLServerHandler.instance().getServer().commandManager.executeCommand(FMLServerHandler.instance().getServer(), cmd);
                return new MinecraftMessage(MinecraftMessage.MCMessageType.CRATE_RESULT, CrateContentMapper.toDTO(res));
        }

        log.error("Player: {}, Message not valid: " + message, playerName);
        return null;
    }
}
