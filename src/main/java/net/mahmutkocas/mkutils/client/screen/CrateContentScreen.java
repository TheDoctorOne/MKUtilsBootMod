package net.mahmutkocas.mkutils.client.screen;

import lombok.Getter;
import lombok.Setter;
import net.mahmutkocas.mkutils.AppGlobals;
import net.mahmutkocas.mkutils.client.screen.components.crate.CrateContentList;
import net.mahmutkocas.mkutils.common.MinecraftMessage;
import net.mahmutkocas.mkutils.common.dto.CrateDTO;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;

@Getter
@Setter
public class CrateContentScreen extends GuiScreen {

    private final GuiScreen parent;
    private final CrateDTO crateDTO;
    private CrateContentList list;

    public CrateContentScreen(GuiScreen parent, CrateDTO crateDTO) {
        this.parent = parent;
        this.crateDTO = crateDTO;
    }

    @Override
    public void initGui() {
        list = new CrateContentList(this, crateDTO, this.width, this.height, 32, this.height - 64, 36);
        list.setContents();
        this.buttonList.add(new GuiButton(1, 5, 5, 75, 20, "Geri"));
        this.buttonList.add(new GuiButton(2, this.width/2-75, this.height-25, 150, 20, "Kasayi Aç"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if(button.id == 1) {
            parent.mc.displayGuiScreen(parent);
        } else if(button.id == 2) {
            openCrate();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        list.drawScreen(mouseX, mouseY, partialTicks);
        float strLen = fontRenderer.getStringWidth(getCrateDTO().getName());
        fontRenderer.drawString(getCrateDTO().getName(), (this.width-strLen)/2, 5, Color.WHITE.getRGB(),false);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }


    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        list.handleMouseInput();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        list.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        list.mouseReleased(mouseX, mouseY, state);
    }

    private void openCrate() {
        AppGlobals.NETWORK.sendToServer(new MinecraftMessage(MinecraftMessage.MCMessageType.CRATE_OPEN, crateDTO));
        parent.mc.displayGuiScreen(parent);
    }
}
