package net.mahmutkocas.mkutils.client.screen;

import net.mahmutkocas.mkutils.client.ScreenProvider;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;

import java.io.IOException;

public class PauseScreen extends GuiIngameMenu {


    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(100, (this.width-100)/2, 15, 100, 20, "Kasa"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if(button.id == 100) {
            this.mc.displayGuiScreen(ScreenProvider.INSTANCE.getCrateScreen().parent(this));
        }
    }
}
