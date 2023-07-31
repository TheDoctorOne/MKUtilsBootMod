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
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
public class CrateCommandHandler extends CommandBaseExtended {

    private List<Command> commands = CrateCommandFactory.getCommands();

    @Override
    public String getName() {
        return "crate";
    }


    @Override
    public List<Command> getCommands() {
        return commands;
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
