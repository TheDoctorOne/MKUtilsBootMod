package net.mahmutkocas.reservermod.client.network.mc;

import lombok.SneakyThrows;
import net.mahmutkocas.reservermod.client.ClientGlobals;
import net.mahmutkocas.reservermod.common.MinecraftMessage;
import net.mahmutkocas.reservermod.common.dto.ModTokenDTO;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.nio.file.Files;
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
        if(message.getMsg().equals("token")) {
            Path currentPath = Paths.get("");
            File[] fMods = new File(currentPath.toAbsolutePath().toString() + "/mods").listFiles();
            List<String> mods = fMods != null ?
                    Arrays.stream(fMods).map(File::getName).collect(Collectors.toList())
                    :
                    new ArrayList<>();
            return new MinecraftMessage(new ModTokenDTO(ClientGlobals.getUserToken().getToken(), mods));
        }
        return null;
    }
}
