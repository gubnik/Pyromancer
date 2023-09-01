package net.team_prometheus.pyromancer.mixin;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.animations.PyromancerAnimationCore;
import net.team_prometheus.pyromancer.items.blazing_journal.BlazingJournal;
import net.team_prometheus.pyromancer.items.ItemUtils;
import net.team_prometheus.pyromancer.items.embers.Ember;
import net.team_prometheus.pyromancer.items.embers.EmberAttacks;
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
                    if(player instanceof AbstractClientPlayer abstractClientPlayer) {
                        if (ember != Ember.HEAVENLY_FLAME ||
                                (PyromancerAnimationCore.animationData.get(abstractClientPlayer) != null && !PyromancerAnimationCore.animationData.get(abstractClientPlayer).isActive())
                        ) {
                            EmberUtilities.playAnimation(ember);
                        }
                    }
                    if (ember.getAnimationDelay() != 0) {
                         if(ember == Ember.HEAVENLY_FLAME) {
                            if((tick + 10) % 20 == 0){
                                double x = player.getX();
                                double y = player.getY();
                                double z = player.getZ();
                                EmberAttacks.heavenlyFlameSupport(x, y, z, player, ember);
                            }
                         } else {
                             PyromancerMod.queueServerWork(ember.getAnimationDelay(), () -> ember.getFunction().apply(player));
                         }
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
                if (ember.getAnimationDelay() == 0 || ember == Ember.HEAVENLY_FLAME) {
                    cir.setReturnValue(ember.getAnimationDuration());
                } else cir.setReturnValue(1);
            }
        }
    }
    @Inject(method = "releaseUsing", at = @At("HEAD"), cancellable = true)
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity entity, int tick, CallbackInfo cir) {
        if(itemStack.getItem() instanceof TieredItem) {
            String emberTag = itemStack.getOrCreateTag().getString("ember");
            if (Ember.isValidEmber(emberTag) && entity instanceof Player player) {
                EmberUtilities.emberCooldown(player, Ember.byName(emberTag).getCooldown(), emberTag);
                Ember ember = Ember.byName(emberTag);
                if(ember == Ember.HEAVENLY_FLAME && entity instanceof AbstractClientPlayer abstractClientPlayer){
                    EmberUtilities.playAnimation(abstractClientPlayer, "empty");
                }
            }
        }
    }
    @Inject(method = "finishUsingItem", at = @At("HEAD"), cancellable = true)
    public void finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity, CallbackInfoReturnable<ItemStack> cir) {
        if(itemStack.getItem() instanceof TieredItem) {
            String emberTag = itemStack.getOrCreateTag().getString("ember");
            if (Ember.isValidEmber(emberTag) && entity instanceof Player player) {
                Ember ember = Ember.byName(emberTag);
                EmberUtilities.emberCooldown(player, ember.getCooldown(), emberTag);
            }
            cir.setReturnValue(itemStack);
        }
    }
}
