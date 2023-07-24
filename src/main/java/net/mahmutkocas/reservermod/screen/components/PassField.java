package net.mahmutkocas.reservermod.screen.components;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

public class PassField extends GuiTextField {
    public PassField(int componentId, FontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height) {
        super(componentId, fontrendererObj, x, y, par5Width, par6Height);
    }

    private String passText(String text) {
        StringBuilder sb = new StringBuilder();
        for(char c : text.toCharArray()) {
            sb.append('*');
        }
        return sb.toString();
    }

    @Override
    public void drawTextBox() {
        String holder = getText();
        setText(passText(holder));
        super.drawTextBox();
        setText(holder);
    }
}
