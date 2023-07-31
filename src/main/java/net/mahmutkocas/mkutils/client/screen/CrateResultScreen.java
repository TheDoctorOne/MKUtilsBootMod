package net.mahmutkocas.mkutils.client.screen;

import lombok.Getter;
import lombok.Setter;
import net.mahmutkocas.mkutils.client.screen.components.GuiImageDraw;
import net.mahmutkocas.mkutils.common.dto.CrateContentDTO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;

@Getter
@Setter
public class CrateResultScreen extends GuiScreen {

    private GuiScreen parent;

    private CrateContentDTO content;

    private GuiImageDraw imageDraw;

    public CrateResultScreen(GuiScreen parent) {
        this.parent = parent;
        imageDraw = new GuiImageDraw("none");
    }

    public CrateResultScreen content(CrateContentDTO content) {
        setContent(content);
        return this;
    }

    public void setContent(CrateContentDTO content) {
        this.content = content;
        imageDraw.updateResourcePath(content.getImageUrl());
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(1, (width-150)/2, height-30, 150, 20, "Geri"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button.id == 1) {
            Minecraft.getMinecraft().displayGuiScreen(parent);
        }
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        imageDraw.prepareImage(content.getImageUrl());
        imageDraw.drawTextureAt(width/2-48, height/2-48, 96, 96);

        int stringWidth = fontRenderer.getStringWidth(content.getName() + " KAZANDINIZ!");
        fontRenderer.drawString(content.getName() + " KAZANDINIZ!", (width-stringWidth)/2, 30
                , getContent().getColor() != null ? getContent().getColor() : Color.WHITE.getRGB()
        );
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
