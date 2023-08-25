package net.team_prometheus.pyromancer.entity.renderers;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.entity.ModEntities;
import net.team_prometheus.pyromancer.entity.PyromancerBoatEntity;
import net.team_prometheus.pyromancer.entity.PyromancerChestBoatEntity;

@Mod.EventBusSubscriber(modid = PyromancerMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRenderersHandler {
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        // Register boat layer definitions
        LayerDefinition boatLayerDefinition = BoatModel.createBodyModel(false);
        LayerDefinition chestBoatLayerDefinition = BoatModel.createBodyModel(true);
        for (PyromancerBoatEntity.ModelType type : PyromancerBoatEntity.ModelType.values()) {
            ForgeHooksClient.registerLayerDefinition(PyromancerBoatRenderer.createBoatModelName(type), () -> boatLayerDefinition);
            ForgeHooksClient.registerLayerDefinition(PyromancerBoatRenderer.createChestBoatModelName(type), () -> chestBoatLayerDefinition);
        }
        // Register entity renderers
        event.registerEntityRenderer((EntityType<PyromancerBoatEntity>) ModEntities.BOAT.get(), context -> new PyromancerBoatRenderer(context, false));
        event.registerEntityRenderer((EntityType<PyromancerChestBoatEntity>) ModEntities.CHEST_BOAT.get(), context -> new PyromancerBoatRenderer(context, true));
    }
}
