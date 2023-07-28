package net.mahmutkocas.mkutils.client.screen;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.GuiScreen;

@Getter
@Setter
public class CrateResultScreen extends GuiScreen {

    private GuiScreen parent;

    public CrateResultScreen(GuiScreen parent) {
        this.parent = parent;
    }

}
