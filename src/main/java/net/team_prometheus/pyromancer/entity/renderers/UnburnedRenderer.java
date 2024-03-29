package net.team_prometheus.pyromancer.entity.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.entity.models.UnburnedModel;

import net.team_prometheus.pyromancer.entity.unburned.Unburned;
import org.jetbrains.annotations.NotNull;
public class UnburnedRenderer extends MobRenderer<Unburned, UnburnedModel<Unburned>> {
    public UnburnedRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new UnburnedModel<>(renderManager.bakeLayer(UnburnedModel.LAYER_LOCATION)), 0.5f);
        this.shadowRadius = 1f;
    }
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Unburned unburned){
        return new ResourceLocation(PyromancerMod.MOD_ID, "textures/entity/unburned.png");
    }
}
