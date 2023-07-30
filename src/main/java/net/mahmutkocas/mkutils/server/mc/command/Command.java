package net.mahmutkocas.mkutils.server.mc.command;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Command {
    private final String help;
    private final String[] commands;
    private final int minArgLen; // does contain the command itself. Minimum args required.
    private final RunnableCommand onCommand;
}
