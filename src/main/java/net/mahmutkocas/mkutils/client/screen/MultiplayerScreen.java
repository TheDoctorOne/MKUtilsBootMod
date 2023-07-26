package net.mahmutkocas.mkutils.client.screen;

import net.mahmutkocas.mkutils.client.ClientGlobals;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class MultiplayerScreen extends GuiMultiplayer {

    private final GuiScreen parentScreen;
    public MultiplayerScreen(Minecraft mc) {
        super(mc.currentScreen);
        this.mc = mc;
        parentScreen = mc.currentScreen;
    }


    @Override
    public void createButtons() {
        super.createButtons();
        this.buttonList.add(new GuiButton(99, 5, 5, 100, 20, "Giriş Yap"));
        this.buttonList.add(new GuiButton(100, this.width-105, 5, 100, 20, "Kasa"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if(button.id == 99) {
            this.mc.displayGuiScreen(new LoginScreen(this, ClientGlobals.getClientConfig().getUsername()));
        }
        if(button.id == 100) {
            this.mc.displayGuiScreen(new CrateScreen(this));
        }
    }
}
