package net.mahmutkocas.mkutils.server.mc;

import net.mahmutkocas.mkutils.server.mc.command.Command;
import net.mahmutkocas.mkutils.server.mc.command.CommandFactory;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.util.*;
import java.util.stream.Collectors;

public class CrateCommandHandler extends CommandBase {


    private List<Command> commands = CommandFactory.getCommands();

    private String help = buildHelp();


    @Override
    public String getName() {
        return "crate";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return help;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
         boolean isOp = sender.canUseCommand(getRequiredPermissionLevel(), this.getName());
         if(!isOp) {
             return;
         }
         if(args.length == 0) {
             sender.sendMessage(new TextComponentString(getUsage(sender)));
             return;
         }

         List<Command> filtered = commands.stream()
                 .filter(command -> command.getCommands()[0].equals(args[0])).
                 collect(Collectors.toList());

         if(filtered.isEmpty()) {
             sender.sendMessage(new TextComponentString(help));
             return;
         }

         filtered.forEach(command -> {
             if(command.getMinArgLen() == 0) {
                 command.getOnCommand().execute(server, sender, args);
                 return;
             }
             if(args.length < command.getMinArgLen()) {
                 sender.sendMessage(new TextComponentString(command.getHelp()));
                 return;
             }
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

         });
    }

    private String buildHelp() {
        StringBuilder sb = new StringBuilder(512);

        sb.append("Crate Commands\n");

        for(Command cmd : commands) {
            String help = cmd.getHelp();
            String[] cmds = cmd.getCommands();
            sb.append("/crate ");
            for(int i=0; i<cmds.length;i++) {
                sb.append(cmds[i]).append(" ");
            }
            sb.append("- ").append(help).append("\n");
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
