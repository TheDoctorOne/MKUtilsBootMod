package net.mahmutkocas.mkutils.client.screen.components;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.mahmutkocas.mkutils.client.ClientGlobals;
import net.mahmutkocas.mkutils.client.screen.CrateScreen;
import net.mahmutkocas.mkutils.common.dto.CrateContentDTO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;

@Log4j2
@Getter
@Setter
public abstract class ListEntryBase implements GuiListExtended.IGuiListEntry {
    protected static final ResourceLocation UNKNOWN_SERVER = new ResourceLocation("textures/misc/unknown_server.png");

    protected final Minecraft mc;
    protected final ResourceLocation contentIcon;
    protected DynamicTexture icon;
    protected BufferedImage bufferedIcon;

    public ListEntryBase(String resourcePath) {
        this.mc = Minecraft.getMinecraft();
        this.contentIcon = new ResourceLocation("crate/" + resourcePath + "/icon");
        this.icon = (DynamicTexture)this.mc.getTextureManager().getTexture(this.contentIcon);
    }

    protected void drawTextureAt(int x, int y, ResourceLocation resourceLocation)
    {
        this.mc.getTextureManager().bindTexture(resourceLocation);
        GlStateManager.enableBlend();
//        mc.currentScreen.drawTexturedModalRect(x,y, 0,0, 32, 32);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
        GlStateManager.disableBlend();
    }

    protected void prepareImage(String imageUrl)
    {
        if (imageUrl == null)
        {
            this.mc.getTextureManager().deleteTexture(this.contentIcon);
            this.icon = null;
        }
        else
        {
            if(bufferedIcon == null) {
                bufferedIcon = ClientGlobals.getImageByURL(imageUrl);
            }

            if (bufferedIcon != null && this.icon == null)
            {
                this.icon = new DynamicTexture(bufferedIcon.getWidth(), bufferedIcon.getHeight());
                this.mc.getTextureManager().loadTexture(this.contentIcon, this.icon);
                bufferedIcon.getRGB(0, 0, bufferedIcon.getWidth(), bufferedIcon.getHeight(), this.icon.getTextureData(), 0, bufferedIcon.getWidth());
            }

            this.icon.updateDynamicTexture();
        }
    }


}
