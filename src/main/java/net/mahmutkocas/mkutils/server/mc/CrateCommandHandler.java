package net.mahmutkocas.mkutils.server.mc;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.mahmutkocas.mkutils.server.mc.command.Command;
import net.mahmutkocas.mkutils.server.mc.command.CommandBaseExtended;
import net.mahmutkocas.mkutils.server.mc.command.CrateCommandFactory;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
public class CrateCommandHandler extends CommandBaseExtended {

    private List<Command> commands = CrateCommandFactory.getCommands();
    private String help = buildHelpStr();
    private ITextComponent helpMessage = buildHelp();


    @Override
    public String getName() {
        return "crate";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return help;
    }

    @Override
    public List<Command> getCommands() {
        return commands;
    }

    @Override
    public ITextComponent getHelpMessage() {
        return helpMessage;
    }

    @Override
    public void processCommand(Command command, MinecraftServer server, ICommandSender sender, String[] args) {
        List<String> players = new ArrayList<>();

        String[] cmds = command.getCommands();
        int playerNameIndex = -1;
        int crateNameIndex = -1;
        for(int i=1; i<cmds.length;i++) {
            String cmd = cmds[i];
            if(cmd.equals("crateName")) {
                crateNameIndex = i;
            }
            if(cmd.equals("playerName")) {
                players.addAll(buildPlayer(server, args[i]));
                playerNameIndex = i;
            }
        }

        String[] subArgs = handleCrateName(args, crateNameIndex);

        if(players.isEmpty()) {
            command.getOnCommand().execute(server, sender, subArgs);
            return;
        }

        for(String player : players) {
            subArgs[playerNameIndex-1] = player;
            command.getOnCommand().execute(server, sender, subArgs);
        }
    }

    private ITextComponent buildHelp() {
        ITextComponent sb = new TextComponentString("" +
                "============\n" +
                "Crate Commands\n" +
                "============\n");
        sb.getStyle().setBold(true).setColor(TextFormatting.RED);

        for(Command command : commands) {
            String desc = command.getDesc();
            sb.appendSibling(command.getCmdHelp());

            ITextComponent cmd = new TextComponentString("- " + desc + "\n");
            cmd.getStyle().setBold(false).setColor(TextFormatting.WHITE);
            sb.appendSibling(cmd);
        }

        return sb;
    }

    private String buildHelpStr() {
        StringBuilder sb = new StringBuilder(512);

        sb.append("Crate Commands\n");

        for(Command cmd : commands) {
            String desc = cmd.getDesc();

            sb.append(cmd.getCmdHelpStr());
            sb.append("- ").append(desc).append("\n");
        }

        return sb.toString();
    }

    private List<String> buildPlayer(MinecraftServer server, String playerInput) {
        boolean isAll = playerInput.trim().equals("@a");
        if(isAll) {
            return server.getPlayerList()
                    .getPlayers()
                    .stream()
                    .map(player -> player.getName().toLowerCase(Locale.ENGLISH))
                    .collect(Collectors.toList());
        } else  {
            return Collections.singletonList(playerInput);
        }
    }

    private String[] handleCrateName(String[] args, int crateNameIndex) {
        String[] subArgs =
                crateNameIndex < 0
                        ? Arrays.copyOfRange(args, 1, args.length)
                        : Arrays.copyOfRange(args, 1, crateNameIndex +1);
        String[] crateNames =
                crateNameIndex > 0
                        ? Arrays.copyOfRange(args, crateNameIndex, args.length)
                        : null;

        if(crateNames != null) {
            StringBuilder sb = new StringBuilder();
            for (String name : crateNames) {
                sb.append(name).append(' ');
            }
            subArgs[crateNameIndex-1] = sb.toString().trim();
        }
        return subArgs;
    }


}
