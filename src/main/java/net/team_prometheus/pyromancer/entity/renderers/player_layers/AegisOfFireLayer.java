package net.team_prometheus.pyromancer.entity.renderers.player_layers;

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

public class AegisOfFireLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    public static float INFLATION = 1.3F;
    public static float INFLATION_LESSER = 1.1F;
    public static ResourceLocation AEGIS_OF_FIRE = new ResourceLocation("pyromancer:textures/player_overlays/aegis_of_fire.png");

    public AegisOfFireLayer(RenderLayerParent<T, M> owner) {
        super(owner);
    }
    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int p_117351_, @NotNull T entity, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        if (entity instanceof Player player && player.getUseItem().getOrCreateTag().getString("ember").equals("aegis_of_fire")) {
            poseStack.scale(INFLATION, INFLATION_LESSER, INFLATION);

            VertexConsumer vertexconsumer = multiBufferSource.getBuffer(
                    RenderType.entityTranslucent(AEGIS_OF_FIRE)
            );
            this.getParentModel().renderToBuffer(poseStack, vertexconsumer, 15728640, OverlayTexture.NO_OVERLAY,
                    1F, // color
                    1F, // color
                    1F, // color
                    0.2F);
            poseStack.scale(INFLATION_LESSER, 1, INFLATION_LESSER);
            this.getParentModel().renderToBuffer(poseStack, vertexconsumer, 15728640, OverlayTexture.NO_OVERLAY,
                    1F, // color
                    0.5F, // color
                    0.5F, // color
                    0.1F);
        }
    }
}

