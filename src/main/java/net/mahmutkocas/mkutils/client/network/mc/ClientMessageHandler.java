package net.mahmutkocas.mkutils.client.network.mc;

import lombok.SneakyThrows;
import net.mahmutkocas.mkutils.AppGlobals;
import net.mahmutkocas.mkutils.client.ClientGlobals;
import net.mahmutkocas.mkutils.client.ScreenProvider;
import net.mahmutkocas.mkutils.common.MinecraftMessage;
import net.mahmutkocas.mkutils.common.dto.CrateContentDTO;
import net.mahmutkocas.mkutils.common.dto.CrateDTOList;
import net.mahmutkocas.mkutils.common.dto.ModTokenDTO;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SideOnly(Side.CLIENT)
public class ClientMessageHandler implements IMessageHandler<MinecraftMessage, IMessage> {
    @SneakyThrows
    @Override
    public IMessage onMessage(MinecraftMessage message, MessageContext ctx) {
        if(message == null || message.getMsg() == null) {
            return null;
        }

        switch (message.getType()) {
            case CRATE_LIST:
                CrateDTOList crates = message.getMsg(CrateDTOList.class);
                ScreenProvider.INSTANCE.getCrateScreen().getCrateList().setCrates(crates.getCrateDTOs());
                break;
            case CRATE_RESULT:
                CrateContentDTO result = message.getMsg(CrateContentDTO.class);
                ScreenProvider.INSTANCE.getCrateResultScreen().setContent(result);
                Minecraft.getMinecraft().displayGuiScreen(ScreenProvider.INSTANCE.getCrateResultScreen());
                break;
        }
        
        return null;
    }
}
