package net.mahmutkocas.mkutils.client.screen.components;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.mahmutkocas.mkutils.common.dto.CrateDTO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

@Log4j2
@Getter
@Setter
public class CrateListEntry extends ListEntryBase {

    private CrateDTO crateDTO;
    private CrateList owner;
    private long lastClickTime;

    public CrateListEntry(@NotNull CrateList owner,@NotNull CrateDTO crateDTO) {
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
        this.mc.getTextureManager().bindTexture(Gui.ICONS);
        Gui.drawModalRectWithCustomSizedTexture(x + listWidth - 15, y, 0, 0, 10, 8, 256.0F, 256.0F);

        prepareImage(getCrateDTO().getImageUrl());

        if (this.icon != null)
        {
            this.drawTextureAt(x, y, getContentIcon());
        }
        else
        {
            this.drawTextureAt(x, y, UNKNOWN_SERVER);
        }
        this.mc.fontRenderer.drawString(getCrateDTO().getName(), x + 32 + 3, y + 1, 16777215);
    }



    @Override
    public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {

        owner.setSelectedCrateIndex(slotIndex);

        if (Minecraft.getSystemTime() - this.lastClickTime < 250L)
        {
            this.owner.openSelected();
        }

        this.lastClickTime = Minecraft.getSystemTime();

        return false;
    }

    @Override
    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {

    }
}
