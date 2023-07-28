package net.mahmutkocas.mkutils.client.screen.components.crate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.mahmutkocas.mkutils.client.screen.components.ListEntryBase;
import net.mahmutkocas.mkutils.common.dto.CrateContentDTO;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

@Log4j2
@Getter
@Setter
public class CrateContentEntry extends ListEntryBase {

    private CrateContentDTO contentDTO;
    private GuiScreen parent;

    public CrateContentEntry(GuiScreen parent, CrateContentDTO contentDTO) {
        super(contentDTO.getImageUrl());
        this.parent = parent;
        this.contentDTO = contentDTO;
    }

    @Override
    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, float partialTicks) {

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Gui.ICONS);
        Gui.drawModalRectWithCustomSizedTexture(x + listWidth - 15, y, 0, 0, 10, 8, 256.0F, 256.0F);

        prepareImage(getContentDTO().getImageUrl());

        if (this.icon != null)
        {
            this.drawTextureAt(x, y, getContentIcon());
        }
        else
        {
            this.drawTextureAt(x, y, UNKNOWN_SERVER);
        }
        this.mc.fontRenderer.drawString(getContentDTO().getName(), x + 32 + 3, y + 1, 16777215);
    }



    @Override
    public void updatePosition(int slotIndex, int x, int y, float partialTicks) {
    }

    @Override
    public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {
        return false;
    }

    @Override
    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {    }
}
