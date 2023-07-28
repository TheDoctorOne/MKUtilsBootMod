package net.mahmutkocas.mkutils.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.mahmutkocas.mkutils.client.screen.CrateContentScreen;
import net.mahmutkocas.mkutils.client.screen.CrateResultScreen;
import net.mahmutkocas.mkutils.client.screen.CrateScreen;
import net.mahmutkocas.mkutils.client.screen.LoginScreen;
import net.minecraft.client.Minecraft;

@Getter
@RequiredArgsConstructor
public class ScreenProvider {
    private final CrateScreen crateScreen;
    private final CrateContentScreen crateContentScreen;
    private final CrateResultScreen crateResultScreen;
    private final LoginScreen loginScreen;

    public static ScreenProvider INSTANCE;

    public static void initialize(Minecraft mc) {
        INSTANCE = new ScreenProvider(
                new CrateScreen(mc.currentScreen),
                new CrateContentScreen(mc.currentScreen, null),
                new CrateResultScreen(mc.currentScreen),
                new LoginScreen(mc, ClientGlobals.getClientConfig().getUsername())
        );
    }
}
