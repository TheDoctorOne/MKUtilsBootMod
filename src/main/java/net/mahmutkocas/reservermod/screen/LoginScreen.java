package net.mahmutkocas.reservermod.screen;

import lombok.extern.log4j.Log4j2;
import net.mahmutkocas.reservermod.AppGlobals;
import net.mahmutkocas.reservermod.screen.components.PassField;
import net.mahmutkocas.reservermod.web.dto.TokenDTO;
import net.mahmutkocas.reservermod.web.dto.UserLoginDTO;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

@Log4j2
public class LoginScreen extends GuiScreen {

    private final GuiScreen parent;

    private GuiTextField userField;
    private PassField passField;

    private LoginState loginState;
    private String loginFailReason;

    public LoginScreen(GuiScreen parent) {
        this.parent = parent;
        this.mc = parent.mc;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        userField = new GuiTextField(0, this.fontRenderer, this.width / 2 - 100, 66, 200, 20);
        passField = new PassField(1, this.fontRenderer, this.width / 2 - 100, 106, 200, 20);

        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 18, "Giris"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 18, "Vazgec"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if(button.id == 0) {
            onLoginButton();
        } else if(button.id == 1) {
            mc.displayGuiScreen(parent);
        }
    }

    private void onLoginButton() {
        try {
            ResponseEntity<TokenDTO> response = AppGlobals.getUserClient().login(new UserLoginDTO(userField.getText(), passField.getText()));
            if(!response.getStatusCode().is2xxSuccessful()) {
                loginState = LoginState.SUCCESS;
                loginFailReason = response.getBody().getToken();
                return;
            }
            loginState = LoginState.FAIL;
        } catch (RuntimeException rte) {
            log.error("Server connection exception!", rte);
            loginState = LoginState.SERVER_ERROR;
        }
    }


    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        this.userField.textboxKeyTyped(typedChar, keyCode);
        this.passField.textboxKeyTyped(typedChar, keyCode);
    }


    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.userField.mouseClicked(mouseX, mouseY,  mouseButton);
        this.passField.mouseClicked(mouseX, mouseY,  mouseButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "Giris Yap", this.width / 2, 17, 16777215);
        this.drawString(this.fontRenderer, "Kullanıcı Adı", this.width / 2 - 100, 53, 10526880);
        this.drawString(this.fontRenderer, "Parola", this.width / 2 - 100, 94, 10526880);
        userField.drawTextBox();
        passField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private enum LoginState {
        NONE(""),
        FAIL("Başarısız"),
        SERVER_ERROR("Sunucuya Bağlanılamıyor"),
        SUCCESS("Başarılı!");
        private final String info;
        LoginState(String info) {
            this.info = info;
        }
        public String getInfo() {
            return info;
        }
    }
}