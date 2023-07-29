package net.team_prometheus.pyromancer.entity.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class AshenFormLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    public static float INFLATION = 1.2F;
    public static ResourceLocation ASHEN_FORM = new ResourceLocation("pyromancer:textures/player_overlays/ashen_form.png");

    public AshenFormLayer(RenderLayerParent<T, M> owner) {
        super(owner);
    }
    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int p_117351_, @NotNull T entity, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        if (entity instanceof Player player && player.getUseItem().getOrCreateTag().getString("ember").equals("ashen_form")) {
            poseStack.scale(INFLATION, INFLATION, INFLATION);
            poseStack.translate(
                    player.getLookAngle().x * -0.05,
                    player.getLookAngle().y * -0.05,
                    player.getLookAngle().z * -0.05);
            VertexConsumer vertexconsumer = multiBufferSource.getBuffer(
                    RenderType.entityTranslucent(ASHEN_FORM)
            );
            this.getParentModel().renderToBuffer(poseStack, vertexconsumer, 15728640, OverlayTexture.NO_OVERLAY,
                    1F, // color
                    1F, // color
                    1F, // color
                    0.6F);
        }
    }
}

