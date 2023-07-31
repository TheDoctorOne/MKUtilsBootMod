package net.mahmutkocas.mkutils.client.screen;

import feign.FeignException;
import lombok.extern.log4j.Log4j2;
import net.mahmutkocas.mkutils.client.ClientGlobals;
import net.mahmutkocas.mkutils.client.enums.AuthStateRegister;
import net.mahmutkocas.mkutils.client.screen.components.PassField;
import net.mahmutkocas.mkutils.common.dto.RegisterResponseDTO;
import net.mahmutkocas.mkutils.common.dto.UserDTO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

@Log4j2
public class RegisterScreen extends GuiScreen {

    private GuiScreen parent;

    private GuiTextField userField;
    private PassField passField;
    private GuiTextField discordField;

    private String initialUserName;

    private AuthStateRegister registerState = AuthStateRegister.NONE;

    public RegisterScreen(Minecraft mc) {
        this.mc = mc;
        this.parent = mc.currentScreen;
        initialUserName = mc.getSession().getUsername().split(";")[0];
    }

    public RegisterScreen parent(GuiScreen parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        userField = new GuiTextField(0, this.fontRenderer, this.width / 2 - 100, 66, 200, 20);
        passField = new PassField(1, this.fontRenderer, this.width / 2 - 100, 106, 200, 20);
        discordField = new GuiTextField(1, this.fontRenderer, this.width / 2 - 100, 146, 200, 20);

        userField.setText(initialUserName);

        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 18, "Kayıt Ol"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 18, "Geri"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if(button.id == 0) {
            onRegisterButton();
        } else if(button.id == 1) {
            mc.displayGuiScreen(parent);
        }
    }

    private void onRegisterButton() {
        try {
            register();
        } catch (FeignException unauthorized){
            log.error("Server unauthorized!", unauthorized);
            registerState = AuthStateRegister.FAIL;
        } catch (RuntimeException rte) {
            log.error("Server connection exception!", rte);
            registerState = AuthStateRegister.SERVER_ERROR;
        }
    }

    private void register() {
        ClientGlobals.getClientConfig().setUsername(userField.getText());
        ClientGlobals.saveConfig();

        RegisterResponseDTO response = ClientGlobals.getUserClient().register(new UserDTO(userField.getText(), passField.getText(), discordField.getText()));
        if(!response.getSuccess()) {
            registerState = AuthStateRegister.FAIL;
            return;
        }
        registerState = AuthStateRegister.SUCCESS;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        this.userField.textboxKeyTyped(typedChar, keyCode);
        this.passField.textboxKeyTyped(typedChar, keyCode);
        this.discordField.textboxKeyTyped(typedChar, keyCode);
    }


    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.userField.mouseClicked(mouseX, mouseY,  mouseButton);
        this.passField.mouseClicked(mouseX, mouseY,  mouseButton);
        this.discordField.mouseClicked(mouseX, mouseY,  mouseButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "Kayıt Ol", this.width / 2, 17, 16777215);
        this.drawString(this.fontRenderer, "Kullanıcı Adı", this.width / 2 - 100, 53, 10526880);
        this.drawString(this.fontRenderer, "Parola", this.width / 2 - 100, 94, 10526880);
        this.drawString(this.fontRenderer, "Discord (Şifrenizi unutursanız bu hesaptan yazınız.)", this.width / 2 - 100, 135, 10526880);
        this.drawString(this.fontRenderer, registerState.getInfo(), this.width / 2 - 100, this.height / 4 + 120 + 18 + 25, registerState.getColorInt());
        userField.drawTextBox();
        passField.drawTextBox();
        discordField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
