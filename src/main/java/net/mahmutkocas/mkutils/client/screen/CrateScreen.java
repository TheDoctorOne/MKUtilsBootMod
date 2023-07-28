package net.mahmutkocas.mkutils.client.screen;

import lombok.Getter;
import lombok.Setter;
import net.mahmutkocas.mkutils.client.screen.components.crate.CrateList;
import net.mahmutkocas.mkutils.common.dto.CrateContentDTO;
import net.mahmutkocas.mkutils.common.dto.CrateDTO;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@Setter
@Getter
public class CrateScreen extends GuiScreen {

    private CrateList crateList;
    private GuiScreen parent;


    public CrateScreen(GuiScreen parent) {
        this.parent = parent;
        crateList = new CrateList(this, this.width, this.height, 32, this.height - 32, 36);
    }

    public CrateScreen parent(GuiScreen parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public void initGui() {
        crateList.setDimensions(this.width, this.height, 32, this.height - 32);
//        crateList.setCrates(Arrays.asList(
//                createCrate(1L),
//                new CrateDTO(2L, "Gengar", "https://pokemongostop.org/images/pokemon/gengar.png", new ArrayList<>(), Color.magenta.getRGB()),
//                new CrateDTO(3L, "Pikachu", "https://mito3dprint.nyc3.digitaloceanspaces.com/3dmodels/suggestions/list/pokemon.png", new ArrayList<>(), Color.pink.getRGB()),
//                new CrateDTO(4L, "Gengar", "https://pokemongostop.org/images/pokemon/gengar.png", new ArrayList<>(), Color.red.getRGB()),
//                new CrateDTO(5L, "Pikachu", "https://mito3dprint.nyc3.digitaloceanspaces.com/3dmodels/suggestions/list/pokemon.png", new ArrayList<>(), Color.BLUE.getRGB()),
//                new CrateDTO(6L, "Gengar", "https://pokemongostop.org/images/pokemon/gengar.png", new ArrayList<>(), Color.YELLOW.getRGB()),
//                new CrateDTO(7L, "Pikachu", "https://mito3dprint.nyc3.digitaloceanspaces.com/3dmodels/suggestions/list/pokemon.png", new ArrayList<>(), Color.CYAN.getRGB()),
//                new CrateDTO(8L, "Gengar", "https://pokemongostop.org/images/pokemon/gengar.png", new ArrayList<>(), Color.GREEN.getRGB())
//        ));
        this.buttonList.add(new GuiButton(1, this.width/2-75, 5, 150, 20, "Geri"));
        this.buttonList.add(new GuiButton(2, this.width/2-75, this.height-25, 150, 20, "Kasayı Göster"));
    }

    private CrateDTO createCrate(Long id) {
        return new CrateDTO(id, "Pikachu", "https://mito3dprint.nyc3.digitaloceanspaces.com/3dmodels/suggestions/list/pokemon.png",
                Arrays.asList(
                    new CrateContentDTO("Dikachu", "https://mito3dprint.nyc3.digitaloceanspaces.com/3dmodels/suggestions/list/pokemon.png", 10, Color.magenta.getRGB()),
                    new CrateContentDTO("Gengar", "https://pokemongostop.org/images/pokemon/gengar.png", 10, Color.WHITE.getRGB()),
                    new CrateContentDTO("DimDikachu", "https://mito3dprint.nyc3.digitaloceanspaces.com/3dmodels/suggestions/list/pokemon.png", 10, Color.ORANGE.getRGB())
                ), Color.orange.getRGB());
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if(button.id == 1) {
            parent.mc.displayGuiScreen(parent);
        } else if(button.id == 2) {
            crateList.onSelectedAction();
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        crateList.handleMouseInput();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        crateList.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        crateList.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        crateList.drawScreen(mouseX, mouseY, partialTicks);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
