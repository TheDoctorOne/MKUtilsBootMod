package net.mahmutkocas.mkutils.client.screen;

import net.mahmutkocas.mkutils.client.screen.components.CrateList;
import net.mahmutkocas.mkutils.common.dto.CrateDTO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CrateScreen extends GuiScreen {

    private CrateList crateList;
    private final GuiScreen parent;


    public CrateScreen(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        super.initGui();
        crateList = new CrateList( this.width, this.height, 32, this.height - 64, 36);
        crateList.setCrates(Arrays.asList(
                new CrateDTO(1L, "Pikachu", "https://mito3dprint.nyc3.digitaloceanspaces.com/3dmodels/suggestions/list/pokemon.png", new ArrayList<>()),
                new CrateDTO(2L, "Gengar", "https://pokemongostop.org/images/pokemon/gengar.png", new ArrayList<>()),
                new CrateDTO(3L, "Pikachu", "https://mito3dprint.nyc3.digitaloceanspaces.com/3dmodels/suggestions/list/pokemon.png", new ArrayList<>()),
                new CrateDTO(4L, "Gengar", "https://pokemongostop.org/images/pokemon/gengar.png", new ArrayList<>()),
                new CrateDTO(5L, "Pikachu", "https://mito3dprint.nyc3.digitaloceanspaces.com/3dmodels/suggestions/list/pokemon.png", new ArrayList<>()),
                new CrateDTO(6L, "Gengar", "https://pokemongostop.org/images/pokemon/gengar.png", new ArrayList<>()),
                new CrateDTO(7L, "Pikachu", "https://mito3dprint.nyc3.digitaloceanspaces.com/3dmodels/suggestions/list/pokemon.png", new ArrayList<>()),
                new CrateDTO(8L, "Gengar", "https://pokemongostop.org/images/pokemon/gengar.png", new ArrayList<>())
        ));
        this.buttonList.add(new GuiButton(1, this.width/2-75, 5, 150, 20, "Geri"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if(button.id == 1) {
            parent.mc.displayGuiScreen(parent);
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
        crateList.drawScreen(mouseX, mouseY, partialTicks);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
