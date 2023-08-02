package net.team_prometheus.pyromancer.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.items.blazing_journal.BlazingJournal;
import net.team_prometheus.pyromancer.items.ItemUtils;
import net.team_prometheus.pyromancer.items.embers.Ember;
import net.team_prometheus.pyromancer.items.embers.EmberUtilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
@SuppressWarnings("unused")
public abstract class ItemMixin {
    @Inject(method="use", at = @At("HEAD"), cancellable = true)
    public void use(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        String emberTag = itemStack.getOrCreateTag().getString("ember");
        if(itemStack.getItem() instanceof TieredItem && Ember.isValidEmber(emberTag)
        && player.getOffhandItem().getItem() instanceof BlazingJournal
                && player.getOffhandItem().getOrCreateTag().getInt("blaze") - Ember.byName(emberTag).getCost() >= 0) {
            InteractionResultHolder<ItemStack> result;
            if (!emberTag.equals("")
                    && itemStack.getItem() instanceof TieredItem) {
                player.startUsingItem(interactionHand);
                result = InteractionResultHolder.success(itemStack);
            } else result = InteractionResultHolder.pass(itemStack);
            cir.setReturnValue(result);
        }
    }
    @Inject(method="onUseTick", at = @At("HEAD"), cancellable = true)
    public void onUseTick(Level level, LivingEntity entity, ItemStack itemStack, int tick, CallbackInfo cir) {
        if(itemStack.getItem() instanceof TieredItem && entity instanceof Player player
        && player.getOffhandItem().getItem() instanceof BlazingJournal) {
            String emberTag = itemStack.getOrCreateTag().getString("ember");
            if (Ember.isValidEmber(emberTag)) {
                Ember ember = Ember.byName(emberTag);
                if (ember.getWeaponType().getWeapon().isInstance(itemStack.getItem())
                    && player.getOffhandItem().getOrCreateTag().getInt("blaze") - ember.getCost() >= 0){
                    ItemUtils.changeBlaze(player, -1 * ember.getCost());
                    EmberUtilities.playAnimation(ember);
                    if (ember.getAnimationDelay() != 0) {
                        PyromancerMod.queueServerWork(ember.getAnimationDelay(), () -> ember.getFunction().apply(player));
                        //EmberUtilities.emberCooldown(player, ember.getCooldown(), emberTag);
                    } else if(!ember.equals(Ember.AEGIS_OF_FIRE)){
                        ember.getFunction().apply(player);
                    }
                }
            }
        }
    }
    @Inject(method="getUseDuration", at = @At("HEAD"), cancellable = true)
    public void getUseDuration(ItemStack itemStack, CallbackInfoReturnable<Integer> cir){
        if(itemStack.getItem() instanceof TieredItem) {
            String emberTag = itemStack.getOrCreateTag().getString("ember");
            if (Ember.isValidEmber(emberTag)) {
                Ember ember = Ember.byName(emberTag);
                if (ember.getAnimationDelay() == 0) {
                    cir.setReturnValue(ember.getAnimationDuration());
                } else cir.setReturnValue(1);
            }
        }
    }
    @Inject(method = "releaseUsing", at = @At("HEAD"), cancellable = true)
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity entity, int tick, CallbackInfo cir) {
        if(itemStack.getItem() instanceof TieredItem) {
            String emberTag = itemStack.getOrCreateTag().getString("ember");
            if (Ember.isValidEmber(emberTag) && entity instanceof Player player)
                EmberUtilities.emberCooldown(player, Ember.byName(emberTag).getCooldown(), emberTag);
        }
    }
    @Inject(method = "finishUsingItem", at = @At("HEAD"), cancellable = true)
    public void finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity, CallbackInfoReturnable<ItemStack> cir) {
        if(itemStack.getItem() instanceof TieredItem) {
            String emberTag = itemStack.getOrCreateTag().getString("ember");
            if (Ember.isValidEmber(emberTag) && entity instanceof Player player)
                EmberUtilities.emberCooldown(player, Ember.byName(emberTag).getCooldown(), emberTag);
            cir.setReturnValue(itemStack);
        }
    }
}
