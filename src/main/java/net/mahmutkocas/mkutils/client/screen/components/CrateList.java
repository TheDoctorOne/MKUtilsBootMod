package net.mahmutkocas.mkutils.client.screen.components;

import lombok.Getter;
import lombok.Setter;
import net.mahmutkocas.mkutils.AppGlobals;
import net.mahmutkocas.mkutils.common.MinecraftMessage;
import net.mahmutkocas.mkutils.common.dto.CrateDTO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class CrateList extends GuiListExtended {

    private final List<CrateListEntry> crates = new ArrayList<>();
    private int selectedCrateIndex;

    public CrateList(int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
        super(Minecraft.getMinecraft(), widthIn, heightIn, topIn, bottomIn, slotHeightIn);
    }

    public void setCrates(List<CrateDTO> crateDTOs) {
        crateDTOs.sort(Comparator.comparing(CrateDTO::getId));
        for(CrateDTO dto : crateDTOs) {
            crates.add(new CrateListEntry(this, dto));
        }
    }

    @Override
    public IGuiListEntry getListEntry(int index) {
        if(index >= getSize()) {
            return null;
        }
        return crates.get(index);
    }

    public CrateListEntry getSelectedCrate() {
        if(selectedCrateIndex >= getSize()) {
            return null;
        }
        return crates.get(selectedCrateIndex);
    }

    @Override
    protected int getSize() {
        return crates.size();
    }

    public void openSelected() {
        if(getSelectedCrate() == null) {
            return;
        }
        AppGlobals.NETWORK.sendToServer(new MinecraftMessage(MinecraftMessage.MCMessageType.CRATE_OPEN, getSelectedCrate().getCrateDTO()));
    }

    @Override
    protected boolean isSelected(int slotIndex) {
        return getSelectedCrateIndex() == slotIndex;
    }
}
