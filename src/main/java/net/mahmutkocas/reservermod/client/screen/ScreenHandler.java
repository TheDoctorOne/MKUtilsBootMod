package net.mahmutkocas.reservermod.client.screen;

import lombok.RequiredArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@RequiredArgsConstructor
public class ScreenHandler {

    private final Minecraft mc;

    @SubscribeEvent
    public void redirectScreen(GuiOpenEvent event) {
        if(event.getGui() instanceof GuiMultiplayer) {
            event.setGui(new MultiplayerScreen(mc));
        }
    }
}
