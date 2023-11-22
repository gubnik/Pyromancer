package net.team_prometheus.pyromancer.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.team_prometheus.pyromancer.items.blazing_journal.BlazingJournalItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
@SuppressWarnings("unused")
public abstract class ServerPlayerMixin extends Player {

    public ServerPlayerMixin(Level level, BlockPos blockPos, float v, GameProfile gameProfile, ProfilePublicKey profilePublicKey) {
        super(level, blockPos, v, gameProfile, profilePublicKey);
    }

    @Inject(method = "drop(Z)Z", at = @At("HEAD"), cancellable = true)
    public void dropMixin(boolean val, CallbackInfoReturnable<Boolean> cir) {
        Inventory inventory = this.getInventory();
        ItemStack selected = inventory.getSelected();
        if (selected.getItem() instanceof BlazingJournalItem){
            cir.setReturnValue(false);
        }
    }
}
