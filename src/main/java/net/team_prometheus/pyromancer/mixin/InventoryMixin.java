package net.team_prometheus.pyromancer.mixin;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Inventory.class)
@SuppressWarnings("unused")
public abstract class InventoryMixin {
    @Shadow public Player player;
    @Shadow public abstract ItemStack getSelected();
    @Inject(method = "setPickedItem", at = @At("HEAD"), cancellable = true)
    public void setPickedItem(ItemStack stack, CallbackInfo ci) {
        cancelCI(ci);
    }
    @Inject(method = "pickSlot", at = @At("HEAD"), cancellable = true)
    public void pickSlot(int slot, CallbackInfo ci) {
        cancelCI(ci);
    }
    @Inject(method = "swapPaint", at = @At("HEAD"), cancellable = true)
    public void swapPaint(double slot, CallbackInfo ci) {
        cancelCI(ci);
    }
    private void cancelCI(CallbackInfo ci) {
        ItemStack selected = getSelected();
        if(!selected.getOrCreateTag().getString("ember").equals("")
        && player.getUseItem().equals(selected)){
            ci.cancel();
        }
    }
}
