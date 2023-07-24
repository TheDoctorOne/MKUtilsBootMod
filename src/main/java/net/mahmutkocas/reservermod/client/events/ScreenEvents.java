package net.mahmutkocas.reservermod.client.events;

import lombok.RequiredArgsConstructor;
import net.mahmutkocas.reservermod.client.screen.MultiplayerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@RequiredArgsConstructor
public class ScreenEvents {

    private final Minecraft mc;

    @SubscribeEvent
    public void redirectScreen(GuiOpenEvent event) {
        if(event.getGui() instanceof GuiMultiplayer) {
            event.setGui(new MultiplayerScreen(mc));
        }
    }
}
