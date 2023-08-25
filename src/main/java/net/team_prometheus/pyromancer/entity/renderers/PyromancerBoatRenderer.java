package net.team_prometheus.pyromancer.entity.renderers;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.entity.PyromancerBoatEntity;
import net.team_prometheus.pyromancer.entity.PyromancerChestBoatEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Stream;

public class PyromancerBoatRenderer extends BoatRenderer
{
    private final Map<PyromancerBoatEntity.ModelType, Pair<ResourceLocation, BoatModel>> boatResources;

    public PyromancerBoatRenderer(EntityRendererProvider.Context context, boolean hasChest)
    {
        super(context, false);
        this.boatResources = Stream.of(PyromancerBoatEntity.ModelType.values()).collect(ImmutableMap.toImmutableMap((key) -> key, (model) -> {
            return Pair.of(new ResourceLocation(PyromancerMod.MOD_ID, getTextureLocation(model, hasChest)), createBoatModel(context, model, hasChest));
        }));
    }

    @Override
    public @NotNull Pair<ResourceLocation, BoatModel> getModelWithLocation(@NotNull Boat boat)
    {
        if (boat instanceof PyromancerChestBoatEntity)
            return this.boatResources.get(((PyromancerChestBoatEntity)boat).getModel());
        else
            return this.boatResources.get(((PyromancerBoatEntity)boat).getModel());
    }

    private static String getTextureLocation(PyromancerBoatEntity.ModelType model, boolean hasChest)
    {
        return hasChest ? "textures/entity/chest_boat/" + model.getName() + ".png" : "textures/entity/boat/" + model.getName() + ".png";
    }

    private static ModelLayerLocation createLocation(String name, String layer)
    {
        return new ModelLayerLocation(new ResourceLocation(PyromancerMod.MOD_ID, name), layer);
    }

    public static ModelLayerLocation createBoatModelName(PyromancerBoatEntity.ModelType model)
    {
        return createLocation("boat/" + model.getName(), "main");
    }

    public static ModelLayerLocation createChestBoatModelName(PyromancerBoatEntity.ModelType model)
    {
        return createLocation("chest_boat/" + model.getName(), "main");
    }

    private BoatModel createBoatModel(EntityRendererProvider.Context context, PyromancerBoatEntity.ModelType model, boolean hasChest)
    {
        ModelLayerLocation modellayerlocation = hasChest ? createChestBoatModelName(model) : createBoatModelName(model);
        return new BoatModel(context.bakeLayer(modellayerlocation), hasChest);
    }
}
