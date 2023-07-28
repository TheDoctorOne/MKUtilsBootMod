package net.mahmutkocas.mkutils.client.screen;

import lombok.Getter;
import lombok.Setter;
import net.mahmutkocas.mkutils.client.screen.components.GuiImageDraw;
import net.mahmutkocas.mkutils.common.dto.CrateContentDTO;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;

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

    public void setContent(CrateContentDTO content) {
        this.content = content;
        imageDraw.updateResourcePath(content.getImageUrl());
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        imageDraw.prepareImage(content.getImageUrl());
        imageDraw.drawTextureAt(width/2-32, height/2-32, 64, 64);

        int stringWidth = fontRenderer.getStringWidth(content.getName());
        fontRenderer.drawString(content.getName(), (width-stringWidth)/2, (height-20)/2
                , getContent().getColor() != null ? getContent().getColor() : Color.WHITE.getRGB()
        );
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
