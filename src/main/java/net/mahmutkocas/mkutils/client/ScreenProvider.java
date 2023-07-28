package net.mahmutkocas.mkutils.client;

import lombok.Getter;
import net.mahmutkocas.mkutils.client.screen.CrateContentScreen;
import net.mahmutkocas.mkutils.client.screen.CrateResultScreen;
import net.mahmutkocas.mkutils.client.screen.CrateScreen;
import net.mahmutkocas.mkutils.client.screen.LoginScreen;
import net.minecraft.client.Minecraft;

@Getter
public class ScreenProvider {
    private final CrateScreen crateScreen;
    private final CrateContentScreen crateContentScreen;
    private final CrateResultScreen crateResultScreen;
    private final LoginScreen loginScreen;

    public static ScreenProvider INSTANCE;

    private ScreenProvider(CrateScreen crateScreen, LoginScreen loginScreen) {
        this.crateScreen = crateScreen;
        this.crateContentScreen = new CrateContentScreen(crateScreen, null);
        this.crateResultScreen = new CrateResultScreen(crateScreen);
        this.loginScreen = loginScreen;
    }

    public static void initialize(Minecraft mc) {
        if(INSTANCE != null) {
            return;
        }

        INSTANCE = new ScreenProvider(
                new CrateScreen(mc.currentScreen),
                new LoginScreen(mc, ClientGlobals.getClientConfig().getUsername())
        );
    }
}
