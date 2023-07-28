package net.mahmutkocas.mkutils.client.screen;

import net.minecraft.client.gui.GuiScreen;

public class CrateResultScreen extends GuiScreen {

    private GuiScreen parent;

    public CrateResultScreen(GuiScreen parent) {
        this.parent = parent;
    }

    public CrateResultScreen parent(GuiScreen parent) {
        this.parent = parent;
        return this;
    }
}
