package net.team_prometheus.pyromancer.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import net.team_prometheus.pyromancer.items.weaponary.pyromancy.PyromancyItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ItemRenderer.class)
@SuppressWarnings("unused")
public class ItemRendererMixin {
    @Shadow
    public void renderModelLists(BakedModel p_115190_, ItemStack p_115191_, int p_115192_, int p_115193_, PoseStack p_115194_, VertexConsumer p_115195_){}
    @Shadow
    private ItemColors itemColors;
    @Inject(method = "Lnet/minecraft/client/renderer/entity/ItemRenderer;render(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/resources/model/BakedModel;)V",
    at = @At("HEAD"), cancellable = true)
    private void renderCustomizer(ItemStack itemStack, ItemTransforms.TransformType transformType, boolean b, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, BakedModel bakedModel, CallbackInfo callbackInfo){
        if(itemStack.getItem() instanceof PyromancyItem && itemStack.getOrCreateTag().getBoolean("c")){
            bakedModel = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(poseStack, bakedModel, transformType, b);
            if(itemStack.getItem() instanceof PyromancyItem pyromancyItem && pyromancyItem.SHRINK) {
                poseStack.translate(-0.75D, 0.2D, -0.5D);
            } else {
                poseStack.translate(0D, 0.2D, -0.2D);
            }
            for(RenderType renderType : bakedModel.getRenderTypes(itemStack, true)) {
                VertexConsumer vertex = multiBufferSource.getBuffer(renderType);
                vertex.color(255, 128, 0, 0);
                this.renderModelLists(bakedModel, itemStack, i, j, poseStack, vertex);
            }
            callbackInfo.cancel();
        }
    }
    @Inject(method = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderQuadList(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Ljava/util/List;Lnet/minecraft/world/item/ItemStack;II)V",
            at = @At("HEAD"), cancellable = true)
    public void renderQuadList(PoseStack poseStack, VertexConsumer vertexConsumer, List<BakedQuad> bakedQuads, ItemStack itemStack, int p_115167_, int p_115168_, CallbackInfo callbackInfo){
        if(itemStack.getItem() instanceof PyromancyItem && itemStack.getOrCreateTag().getBoolean("c")) {
            PoseStack.Pose posestack$pose = poseStack.last();
            for (BakedQuad bakedquad : bakedQuads) {
                float f = 1f;
                float f1 = 0.5f;
                float f2 = 0.0f;
                vertexConsumer.putBulkData(posestack$pose, bakedquad, f, f1, f2, 0.5F, p_115167_, p_115168_, false);
            }
            callbackInfo.cancel();
        }
    }
}
