package net.team_prometheus.pyromancer.entity.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.entity.attack_effects.HeavenlyFlameEntity;
import net.team_prometheus.pyromancer.entity.models.HeavenlyFlameModel;
import org.jetbrains.annotations.NotNull;

public class HeavenlyFlameRenderer extends EntityRenderer<HeavenlyFlameEntity> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(PyromancerMod.MOD_ID, "textures/entity/heavenly_flame.png");
    private final HeavenlyFlameModel<HeavenlyFlameEntity> model;
    public HeavenlyFlameRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new HeavenlyFlameModel<>(context.bakeLayer(HeavenlyFlameModel.LAYER_LOCATION));
    }
    public void render(@NotNull HeavenlyFlameEntity entity, float p_114529_, float p_114530_, @NotNull PoseStack poseStack, MultiBufferSource multiBufferSource, int p_114533_){
        VertexConsumer vertexconsumer = multiBufferSource.getBuffer(RenderType.entityTranslucentEmissive(TEXTURE_LOCATION));
        int tick = entity.tickCount;
        float scale = entity.getSize();
        float phase = (5 - Mth.abs(tick - 5));
        float mx = 0.2f * phase;
        float my = 0.2f * phase;
        float mz = 0.2f * phase;
        poseStack.scale(
                mx * scale,
                -1 * my * scale,
                mz * scale
        );
        //poseStack.translate(0D, -1 * my * (57D/16D), 0D);
        this.model.renderToBuffer(poseStack, vertexconsumer, p_114533_, OverlayTexture.NO_OVERLAY, 1.0F, phase/5, phase/5, 1.0F);
        super.render(entity, p_114529_, p_114530_, poseStack, multiBufferSource, p_114533_);
    }
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull HeavenlyFlameEntity p_114482_) {
        return TEXTURE_LOCATION;
    }
}
