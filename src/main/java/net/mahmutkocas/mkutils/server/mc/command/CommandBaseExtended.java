package net.mahmutkocas.mkutils.server.mc.command;

import lombok.extern.log4j.Log4j2;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public abstract class CommandBaseExtended extends CommandBase {

    public abstract ITextComponent getHelpMessage();

    public abstract List<Command> getCommands();

    protected abstract void processCommand(Command command, MinecraftServer server, ICommandSender sender, String[] args);


    protected ITextComponent buildHelp() {
        ITextComponent sb = new TextComponentString(
                "============\n" +
                StringUtils.capitalize(getName()) + " Commands\n" +
                "============\n");
        sb.getStyle().setBold(true).setColor(TextFormatting.RED);

        for(Command command : getCommands()) {
            String desc = command.getDesc();
            sb.appendSibling(command.getCmdHelp());

            ITextComponent cmd = new TextComponentString("- " + desc + "\n");
            cmd.getStyle().setBold(false).setColor(TextFormatting.WHITE);
            sb.appendSibling(cmd);
        }

        return sb;
    }

    protected String buildHelpStr() {
        StringBuilder sb = new StringBuilder(512);

        sb.append(StringUtils.capitalize(getName()) + " Commands\n");

        for(Command cmd : getCommands()) {
            String desc = cmd.getDesc();

            sb.append(cmd.getCmdHelpStr());
            sb.append("- ").append(desc).append("\n");
        }

        return sb.toString();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        boolean isOp = sender.canUseCommand(getRequiredPermissionLevel(), this.getName());
        if(!isOp) {
            log.info("{} tried to use crate command, args: {}", sender.getName(), Arrays.toString(args));
            return;
        }
        if(args.length == 0) {
            sender.sendMessage(getHelpMessage());
            return;
        }

        List<Command> filtered = getCommands().stream()
                .filter(command -> command.getCommands()[0].equals(args[0])).
                collect(Collectors.toList());

        if(filtered.isEmpty()) {
            sender.sendMessage(getHelpMessage());
            return;
        }

        filtered.forEach(command -> preProcessCommand(command, server, sender, args));
    }

    protected void preProcessCommand(Command command, MinecraftServer server, ICommandSender sender, String[] args) {
        if(command.getMinArgLen() == 0) {
            command.getOnCommand().execute(server, sender, args);
            return;
        }
        if(args.length < command.getMinArgLen()) {
            sender.sendMessage(command.getCmdHelp());
            return;
        }
        processCommand(command, server, sender, args);
    }

}
