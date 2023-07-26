package net.mahmutkocas.mkutils.client.screen;

import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import feign.FeignException;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import net.mahmutkocas.mkutils.client.ClientGlobals;
import net.mahmutkocas.mkutils.client.enums.AuthState;
import net.mahmutkocas.mkutils.client.screen.components.PassField;
import net.mahmutkocas.mkutils.common.dto.TokenDTO;
import net.mahmutkocas.mkutils.common.dto.UserDTO;
import net.mahmutkocas.mkutils.common.dto.UserLoginDTO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.lang.reflect.Field;

@Log4j2
public class LoginScreen extends GuiScreen {

    private final GuiScreen parent;

    private GuiTextField userField;
    private PassField passField;

    private final String initialUserName;

    private AuthState authState = AuthState.NONE;

    public LoginScreen(GuiScreen parent, String username) {
        this.parent = parent;
        this.mc = parent.mc;
        initialUserName = username;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        userField = new GuiTextField(0, this.fontRenderer, this.width / 2 - 100, 66, 200, 20);
        passField = new PassField(1, this.fontRenderer, this.width / 2 - 100, 106, 200, 20);

        userField.setText(initialUserName);

        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 18, "Giris"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 18, "Geri"));
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
            login();
        } catch (FeignException.FeignClientException.Unauthorized unauthorized){
            log.error("Server unauthorized!", unauthorized);
            authState = AuthState.FAIL;
        } catch (RuntimeException rte) {
            log.error("Server connection exception!", rte);
            authState = AuthState.SERVER_ERROR;
        }
    }

    private void login() {
        ClientGlobals.getClientConfig().setUsername(userField.getText());
        ClientGlobals.saveConfig();

        TokenDTO response = ClientGlobals.getUserClient().login(new UserLoginDTO(userField.getText(), passField.getText()));
        if(response == null) {
            authState = AuthState.FAIL;
            return;
        }
        authState = AuthState.SUCCESS;
        ClientGlobals.setUserToken(response);
        changeUsername(userField.getText(), response);
    }

    private void changeUsername(String username, TokenDTO response) {
        Session session = Minecraft.getMinecraft().getSession();
        updateField(session, "username", username + ";" + response.getToken());
    }

    @SneakyThrows
    private void updateField(Session obj, String fieldName, String val) {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, val);
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
        this.drawString(this.fontRenderer, authState.getInfo(), this.width / 2 - 100, this.height / 4 + 120 + 18 + 25, authState.getColorInt());
        userField.drawTextBox();
        passField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
