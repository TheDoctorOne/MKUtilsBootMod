package net.mahmutkocas.mkutils.client.screen.components;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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

    public T getSelected() {
        if(selectedIndex >= getSize()) {
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
