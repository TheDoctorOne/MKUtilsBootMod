package net.mahmutkocas.mkutils.client.screen.components.crate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.mahmutkocas.mkutils.client.screen.components.ListEntry;
import net.mahmutkocas.mkutils.common.dto.CrateDTO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

@Log4j2
@Getter
@Setter
public class CrateListEntry extends ListEntry {

    private CrateDTO crateDTO;
    private CrateList owner;
    private long lastClickTime;

    public CrateListEntry(CrateList owner, CrateDTO crateDTO) {
        super(crateDTO.getImageUrl());
        this.owner = owner;
        this.crateDTO = crateDTO;
    }

    @Override
    public void updatePosition(int slotIndex, int x, int y, float partialTicks) {
    }

    @Override
    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, float partialTicks) {

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        prepareImage(getCrateDTO().getImageUrl());

        if (this.icon != null)
        {
            this.drawTextureAt(x, y, getContentIcon());
        }
        else
        {
            this.drawTextureAt(x, y, UNKNOWN_SERVER);
        }
        this.mc.fontRenderer.drawString(getCrateDTO().getName(), x + 32 + 3, y + slotHeight/2 - 3,
                getCrateDTO().getColor() != null ? getCrateDTO().getColor() : 16777215
        );
    }



    @Override
    public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {

        owner.setSelectedIndex(slotIndex);

        if (Minecraft.getSystemTime() - this.lastClickTime < 250L)
        {
            this.owner.onSelectedAction();
        }

        this.lastClickTime = Minecraft.getSystemTime();

        return false;
    }

    @Override
    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {

    }
}
