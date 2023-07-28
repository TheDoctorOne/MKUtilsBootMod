package net.mahmutkocas.mkutils.client.events;

import lombok.RequiredArgsConstructor;
import net.mahmutkocas.mkutils.AppGlobals;
import net.mahmutkocas.mkutils.client.ClientGlobals;
import net.mahmutkocas.mkutils.client.screen.CrateScreen;
import net.mahmutkocas.mkutils.client.screen.MultiplayerScreen;
import net.mahmutkocas.mkutils.client.screen.PauseScreen;
import net.mahmutkocas.mkutils.common.MinecraftMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
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
        if(event.getGui() instanceof GuiMultiplayer && !(event.getGui() instanceof MultiplayerScreen) ) {
            event.setGui(new MultiplayerScreen(mc.currentScreen));
        } else if(event.getGui() instanceof CrateScreen) {
            AppGlobals.NETWORK.sendToServer(new MinecraftMessage(MinecraftMessage.MCMessageType.GET_CRATES));
        } else if(event.getGui() instanceof GuiIngameMenu && !(event.getGui() instanceof PauseScreen) ) {
            event.setGui(new PauseScreen());
        }
    }
}
