package net.team_prometheus.pyromancer.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.team_prometheus.pyromancer.items.ItemUtils;
import net.team_prometheus.pyromancer.items.blazing_journal.compendium.CompendiumOfFlame;
import net.team_prometheus.pyromancer.items.weaponary.pyromancy.PyromancyItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
@SuppressWarnings("unused")
public class ItemInHandRendererMixin {
    @Shadow
    private ItemRenderer itemRenderer;
    @Inject(method = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
    at = @At("TAIL"), cancellable = true)
    public void renderCompendiumItem(LivingEntity livingEntity, ItemStack itemStack, ItemTransforms.TransformType transformType, boolean b, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci){
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

}
