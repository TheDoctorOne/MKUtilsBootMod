package net.mahmutkocas.mkutils.client.screen.components.crate;

import net.mahmutkocas.mkutils.client.screen.components.ListBase;
import net.mahmutkocas.mkutils.common.dto.CrateContentDTO;
import net.mahmutkocas.mkutils.common.dto.CrateDTO;
import net.minecraft.client.gui.GuiScreen;

import java.util.Comparator;

public class CrateContentList extends ListBase<CrateContentEntry> {

    private final CrateDTO crateDTO;

    public CrateContentList(GuiScreen owner, CrateDTO crateDTO, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
        super(owner, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
        this.crateDTO = crateDTO;
        this.setShowSelectionBox(false);
    }

    public void setContents() {
        crateDTO.getContents().sort(Comparator.comparing(CrateContentDTO::getName));
        for(CrateContentDTO dto : crateDTO.getContents()) {
            this.list.add(new CrateContentEntry(owner, dto));
        }
    }

    @Override
    public void handleSelectedAction() {
    }
}