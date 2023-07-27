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
import net.team_prometheus.pyromancer.items.embers.Ember;
import net.team_prometheus.pyromancer.items.embers.EmberUtilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
@SuppressWarnings("unused")
public class ItemMixin {
    @Inject(method="use", at = @At("HEAD"), cancellable = true)
    public void use(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        InteractionResultHolder<ItemStack> result;
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if(!itemStack.getOrCreateTag().getString("ember").equals("")
        && itemStack.getItem() instanceof TieredItem){
            player.startUsingItem(interactionHand);
            result = InteractionResultHolder.success(itemStack);
        } else result = InteractionResultHolder.pass(itemStack);
        cir.setReturnValue(result);
    }
    @Inject(method="onUseTick", at = @At("HEAD"), cancellable = true)
    public void onUseTick(Level level, LivingEntity entity, ItemStack itemStack, int tick, CallbackInfo cir) {
        // it runs, now to make it actually work
        String emberTag = itemStack.getOrCreateTag().getString("ember");
        if(!emberTag.equals("") && entity instanceof Player player){
            Ember ember = Ember.byName(emberTag);
            EmberUtilities.playAnimation(ember);
            if(ember.getWeaponType().getWeapon().isInstance(itemStack.getItem())){
                if(ember.getAnimationDelay() != 0) {
                    PyromancerMod.queueServerWork(ember.getAnimationDelay(), () -> ember.getFunction().apply(player));
                    EmberUtilities.emberCooldown(player, ember.getCooldown(), emberTag);
                }
            }
        }
    }
    @Inject(method="getUseDuration", at = @At("HEAD"), cancellable = true)
    public void getUseDuration(ItemStack itemStack, CallbackInfoReturnable<Integer> cir){
        String emberTag = itemStack.getOrCreateTag().getString("ember");
        if(!emberTag.equals("")){
            Ember ember = Ember.byName(emberTag);
            if(ember.getAnimationDelay() == 0){
                cir.setReturnValue(ember.getCooldown());
            } else cir.setReturnValue(1);
        }
    }
    @Inject(method = "releaseUsing", at = @At("HEAD"), cancellable = true)
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity entity, int tick, CallbackInfo cir) {
        String emberTag = itemStack.getOrCreateTag().getString("ember");
        if(!emberTag.equals("") && entity instanceof Player player) EmberUtilities.emberCooldown(player, Ember.byName(emberTag).getCooldown(), emberTag);
    }
    @Inject(method = "finishUsingItem", at = @At("HEAD"), cancellable = true)
    public void finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity, CallbackInfoReturnable<ItemStack> cir) {
        String emberTag = itemStack.getOrCreateTag().getString("ember");
        if(!emberTag.equals("") && entity instanceof Player player) EmberUtilities.emberCooldown(player, Ember.byName(emberTag).getCooldown(), emberTag);
        cir.setReturnValue(itemStack);
    }
}
