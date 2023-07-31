package net.mahmutkocas.mkutils.server.mc.command;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

@AllArgsConstructor
@Getter
public class Command {
    private final String desc;

    @Getter(AccessLevel.NONE)
    private String cmdHelpStr;
    @Getter(AccessLevel.NONE)
    private ITextComponent cmdHelp;

    private final String[] commands;
    private final int minArgLen; // does contain the command itself. Minimum args required.
    private final RunnableCommand onCommand;
    @Builder.Default
    private int permLevel = 4; // 4 means OP

    @Builder
    public Command(String desc, String[] commands, int minArgLen, RunnableCommand onCommand) {
        this.desc = desc;
        this.commands = commands;
        this.minArgLen = minArgLen;
        this.onCommand = onCommand;
    }

    public String getCmdHelpStr(String name) {
        if(cmdHelpStr == null) {
            String[] cmds = getCommands();
            StringBuilder sb = new StringBuilder(64);
            sb.append("- /").append(name).append(" ");
            for(int i=0; i<cmds.length;i++) {
                sb.append(cmds[i]).append(" ");
            }
            cmdHelpStr = sb.toString();
        }
        return cmdHelpStr;
    }

    public ITextComponent getCmdHelp(String name) {
        if(cmdHelp == null) {
            cmdHelp = new TextComponentString(getCmdHelpStr(name));
            cmdHelp.getStyle().setBold(true).setColor(TextFormatting.AQUA);
        }
        return cmdHelp;
    }
}
