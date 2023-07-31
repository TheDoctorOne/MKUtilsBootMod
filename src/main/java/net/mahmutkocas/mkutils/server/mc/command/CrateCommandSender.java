package net.mahmutkocas.mkutils.server.mc.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.server.FMLServerHandler;

import javax.annotation.Nullable;

public class CrateCommandSender implements ICommandSender {
    @Override
    public String getName() {
        return "Crate Command";
    }

    @Override
    public boolean canUseCommand(int permLevel, String commandName) {
        return true;
    }

    @Override
    public World getEntityWorld() {
        return FMLServerHandler.instance().getServer().getWorld(0);
    }

    @Nullable
    @Override
    public MinecraftServer getServer() {
        return FMLServerHandler.instance().getServer();
    }

    @Override
    public boolean sendCommandFeedback() {
        return true;
    }
}
