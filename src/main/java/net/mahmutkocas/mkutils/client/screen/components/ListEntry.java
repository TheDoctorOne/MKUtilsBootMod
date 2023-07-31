package net.mahmutkocas.mkutils.client.screen.components;

import net.minecraft.client.gui.GuiListExtended;

public abstract class ListEntry extends GuiImageDraw implements GuiListExtended.IGuiListEntry {

    public ListEntry(String resourcePath) {
        super(resourcePath);
    }

}
