package net.mahmutkocas.mkutils.client.screen;

import net.mahmutkocas.mkutils.client.ClientGlobals;
import net.mahmutkocas.mkutils.client.ScreenProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class MultiplayerScreen extends GuiMultiplayer {

    private final GuiScreen parentScreen;

    /**
     * Unique Screen, Minecraft seems like needs this to initialize every time.
     * */
    public MultiplayerScreen(GuiScreen parent) {
        super(parent);
        this.mc = Minecraft.getMinecraft();
        parentScreen = parent;
    }


    @Override
    public void createButtons() {
        super.createButtons();
        this.buttonList.add(new GuiButton(99, 5, 5, 100, 20, "Kayıt Ol"));
        this.buttonList.add(new GuiButton(100, width-105, 5, 100, 20, "Giriş Yap"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        else if (button.id == 8) {
            this.mc.displayGuiScreen(new MultiplayerScreen(parentScreen));
        } else if(button.id == 99) {
            this.mc.displayGuiScreen(ScreenProvider.INSTANCE.getRegisterScreen().parent(this));
        } else if(button.id == 100) {
            this.mc.displayGuiScreen(ScreenProvider.INSTANCE.getLoginScreen().parent(this).username(ClientGlobals.getClientConfig().getUsername()));
        } else {
            super.actionPerformed(button);
        }
    }
}
