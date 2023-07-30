package net.mahmutkocas.mkutils.server.mc.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public interface RunnableCommand {
    void execute(MinecraftServer server, ICommandSender sender, String... args);
}
