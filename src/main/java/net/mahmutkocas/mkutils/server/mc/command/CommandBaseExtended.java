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

    public abstract List<Command> getCommands();

    public ITextComponent getHelpMessage(ICommandSender sender) {
        return buildHelp(sender);
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return buildHelpStr(sender);
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        log.info("{} tried to use command, args: {}", sender.getName(), Arrays.toString(args));

        if(args.length == 0) {
            sender.sendMessage(getHelpMessage(sender));
            return;
        }
        log.info("{} using command, args: {}", sender.getName(), Arrays.toString(args));

        List<Command> filtered = getCommands().stream()
                .filter(command -> command.getCommands()[0].equals(args[0])).
                collect(Collectors.toList());

        if(filtered.isEmpty()) {
            sender.sendMessage(getHelpMessage(sender));
            return;
        }

        filtered.forEach(command -> checkAndProcess(command, server, sender, args));
    }

    protected void checkAndProcess(Command command, MinecraftServer server, ICommandSender sender, String[] args) {
        boolean canUse = sender.canUseCommand(command.getPermLevel(), this.getName());
        if(!canUse) {
            log.error("{} failed to use command, args: {}", sender.getName(), Arrays.toString(args));
            sender.sendMessage(getNoPermMsg());
            return;
        }

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

    protected void processCommand(Command command, MinecraftServer server, ICommandSender sender, String[] args) {
        String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
        command.getOnCommand().execute(server, sender, subArgs);
    }

    protected ITextComponent getNoPermMsg() {
        ITextComponent textComponent = new TextComponentString("Yetkiniz bulunmamaktadır.");
        textComponent.getStyle().setBold(true).setColor(TextFormatting.RED);
        return textComponent;
    }

    protected ITextComponent buildHelp(ICommandSender sender) {
        String name = getName();
        ITextComponent sb = new TextComponentString(
                "============\n" +
                        StringUtils.capitalize(name) + " Commands\n" +
                        "============\n");
        sb.getStyle().setBold(true).setColor(TextFormatting.RED);

        for(Command command : getCommands()) {
            if(!sender.canUseCommand(command.getPermLevel(), name)) {
                continue;
            }

            String desc = command.getDesc();
            sb.appendSibling(command.getCmdHelp());

            ITextComponent cmd = new TextComponentString("- " + desc + "\n");
            cmd.getStyle().setBold(false).setColor(TextFormatting.WHITE);
            sb.appendSibling(cmd);
        }

        return sb;
    }

    protected String buildHelpStr(ICommandSender sender) {
        String name = getName();
        StringBuilder sb = new StringBuilder(512);

        sb.append(StringUtils.capitalize(name)).append(" Commands\n");

        for(Command command : getCommands()) {
            if(!sender.canUseCommand(command.getPermLevel(), name)) {
                continue;
            }

            String desc = command.getDesc();

            sb.append(command.getCmdHelpStr());
            sb.append("- ").append(desc).append("\n");
        }

        return sb.toString();
    }
}
