package net.mahmutkocas.mkutils.server.mc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IInteractionObject;

public class LoginBlockContainer implements IInteractionObject {
    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new BlockerContainer();
    }

    @Override
    public String getGuiID() {
        return "LoginBlockContainer";
    }

    @Override
    public String getName() {
        return "Giriş yapılıyor...";
    }

    @Override
    public boolean hasCustomName() {
        return true;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }

    public static class BlockerContainer extends Container {
        @Override
        public boolean canInteractWith(EntityPlayer playerIn) {
            return false;
        }
    }
}
