package net.team_prometheus.pyromancer.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.team_prometheus.pyromancer.registries.ModItems;
import net.team_prometheus.pyromancer.util.ItemUtils;
import net.team_prometheus.pyromancer.items.compendium.CompendiumOfFlame;
import net.team_prometheus.pyromancer.items.weaponary.pyromancy.PyromancyItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
@SuppressWarnings("unused")
public abstract class ItemInHandRendererMixin {
    @Shadow
    private ItemRenderer itemRenderer;
    @Shadow
    public abstract void renderItem(LivingEntity p_109323_, ItemStack p_109324_, ItemTransforms.TransformType p_109325_, boolean p_109326_, PoseStack p_109327_, MultiBufferSource p_109328_, int p_109329_);
    @Shadow
    public abstract void applyItemArmTransform(PoseStack poseStack, HumanoidArm arm, float f);
    @Inject(method = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At("TAIL"), cancellable = true)
    public void renderItemMixinTail(LivingEntity livingEntity, ItemStack itemStack, ItemTransforms.TransformType transformType, boolean b, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci){
        if(itemStack.getItem() instanceof CompendiumOfFlame && (transformType.equals(ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND) || transformType.equals(ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND))
                && livingEntity.getMainHandItem().equals(itemStack) && !livingEntity.swinging) {
            ItemStack toRender = ItemStack.EMPTY;
            ItemStack activeItem = ItemUtils.getItemFromItem(itemStack, itemStack.getOrCreateTag().getInt("active_slot"));
            if(activeItem != ItemStack.EMPTY){
                toRender = activeItem;
            }
            if(!toRender.equals(ItemStack.EMPTY)) {
                toRender.getOrCreateTag().putBoolean("c", true);
                if(toRender.getItem() instanceof PyromancyItem pyromancyItem && pyromancyItem.SHRINK) {
                    poseStack.scale(0.5f, 0.5f, 0.5f);
                } else {
                    poseStack.scale(0.65f, 0.65f, 0.65f);
                }
                this.itemRenderer.renderStatic(toRender, transformType, i, OverlayTexture.RED_OVERLAY_V, poseStack, multiBufferSource, livingEntity.getId() + transformType.ordinal());
                ci.cancel();
            }
        }
    }
    @Inject(method = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At("HEAD"), cancellable = true)
    public void renderItemMixinHead(LivingEntity livingEntity, ItemStack itemStack, ItemTransforms.TransformType transformType, boolean b, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci){
        if(itemStack.getItem().equals(ModItems.BLAZING_JOURNAL.get())
        && livingEntity instanceof Player player && player.getItemInHand(player.getUsedItemHand()).getItem() instanceof PyromancyItem
        && player.getUseItemRemainingTicks() > 0) {
            ci.cancel();
        }
    }
    @Inject(method = "renderArmWithItem",
            at = @At("HEAD"), cancellable = true)
    private void renderArmWithItemMixin(AbstractClientPlayer player, float v, float v1, InteractionHand hand, float v2, ItemStack itemStack, float v3, PoseStack poseStack, MultiBufferSource multiBufferSource, int i1,
                                        CallbackInfo callbackInfo) {
        if(!(itemStack.getItem() instanceof PyromancyItem) || !(itemStack.equals(player.getItemInHand(hand)))) return;
        if(player.getUsedItemHand().equals(hand) && player.getUseItemRemainingTicks() > 0){
            this.applyItemArmTransform(poseStack, hand.equals(InteractionHand.MAIN_HAND) ? HumanoidArm.RIGHT : HumanoidArm.LEFT, v3);
            this.renderItem(player, itemStack, ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND,
                    false,
                    poseStack,
                    multiBufferSource,
                    i1);
            callbackInfo.cancel();
        }
        //poseStack.popPose();
    }

}
