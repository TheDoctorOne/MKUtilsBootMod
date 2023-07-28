package net.mahmutkocas.mkutils.client.screen.components.crate;

import lombok.Getter;
import lombok.Setter;
import net.mahmutkocas.mkutils.client.ScreenProvider;
import net.mahmutkocas.mkutils.client.screen.CrateContentScreen;
import net.mahmutkocas.mkutils.client.screen.CrateScreen;
import net.mahmutkocas.mkutils.client.screen.components.ListBase;
import net.mahmutkocas.mkutils.common.dto.CrateDTO;

import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class CrateList extends ListBase<CrateListEntry> {

    public CrateList(CrateScreen owner, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
        super(owner, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
    }

    public void setCrates(List<CrateDTO> crateDTOs) {
        crateDTOs.sort(Comparator.comparing(CrateDTO::getId));
        for(CrateDTO dto : crateDTOs) {
            list.add(new CrateListEntry(this, dto));
        }
    }

    @Override
    public void handleSelectedAction() {
        owner.mc.displayGuiScreen(
                ScreenProvider.INSTANCE.getCrateContentScreen()
                        .parent(owner)
                        .crate(getSelected().getCrateDTO())
        );
    }
}
