package net.mahmutkocas.mkutils.server.mc.command.factory;

import net.mahmutkocas.mkutils.server.mc.command.Command;

import java.util.Collections;
import java.util.List;

public abstract class BaseCommandFactory {

    protected BaseCommandFactory() {
        initCommands();
    }

    protected abstract void initCommands();

}
