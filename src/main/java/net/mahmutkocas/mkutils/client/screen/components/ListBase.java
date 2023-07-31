package net.mahmutkocas.mkutils.client.screen.components;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Log4j2
public abstract class ListBase<T extends GuiListExtended.IGuiListEntry> extends GuiListExtended {

    protected final List<T> list = new ArrayList<>();
    protected final GuiScreen owner;
    protected int selectedIndex;

    public ListBase(GuiScreen owner, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
        super(Minecraft.getMinecraft(), widthIn, heightIn, topIn, bottomIn, slotHeightIn);
        this.owner = owner;
    }

    protected abstract void handleSelectedAction();

    @Override
    public IGuiListEntry getListEntry(int index) {
        if(index >= getSize()) {
            return null;
        }
        return list.get(index);
    }

    @Override
    protected void updateItemPos(int entryID, int insideLeft, int yPos, float partialTicks) {
        if(this.getListEntry(entryID) == null) {
            return;
        }
        try {
            this.getListEntry(entryID).updatePosition(entryID, insideLeft, yPos, partialTicks);
        } catch (Exception ex) {
            log.error("updateItemPos", ex);
        }
    }

    @Override
    protected void drawSlot(int slotIndex, int xPos, int yPos, int heightIn, int mouseXIn, int mouseYIn, float partialTicks)
    {
        if(this.getListEntry(slotIndex) == null) {
            return;
        }
        try {
            this.getListEntry(slotIndex).drawEntry(slotIndex, xPos, yPos, this.getListWidth(), heightIn, mouseXIn, mouseYIn, this.isMouseYWithinSlotBounds(mouseYIn) && this.getSlotIndexFromScreenCoords(mouseXIn, mouseYIn) == slotIndex, partialTicks);
        } catch (Exception ex) {
            log.error("drawSlot", ex);
        }
    }


    public T getSelected() {
        if(selectedIndex >= getSize() || selectedIndex < 0) {
            return null;
        }
        return list.get(selectedIndex);
    }

    @Override
    protected boolean isSelected(int slotIndex) {
        return slotIndex == getSelectedIndex();
    }

    @Override
    protected int getSize() {
        return list.size();
    }

    public void onSelectedAction() {
        if(getSelected() == null) {
            return;
        }
        handleSelectedAction();
    }
}
