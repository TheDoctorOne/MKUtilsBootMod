package net.mahmutkocas.mkutils.server.mc;

import lombok.extern.log4j.Log4j2;
import net.mahmutkocas.mkutils.server.mc.command.Command;
import net.mahmutkocas.mkutils.server.mc.command.CommandHandleBaseExtended;
import net.mahmutkocas.mkutils.server.mc.command.factory.UserCommandFactory;

import java.util.List;

@Log4j2
public class UserCommandHandler extends CommandHandleBaseExtended {

    private List<Command> commands = UserCommandFactory.getCommands();

    @Override
    public List<Command> getCommands() {
        return commands;
    }

    @Override
    public String getName() {
        return "user";
    }
}
