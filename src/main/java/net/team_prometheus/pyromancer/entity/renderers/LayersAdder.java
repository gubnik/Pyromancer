package net.team_prometheus.pyromancer.entity.renderers;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.team_prometheus.pyromancer.PyromancerMod;

import java.util.function.Function;
@OnlyIn(Dist.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked", "unused"})
@Mod.EventBusSubscriber(modid = PyromancerMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LayersAdder {
    public static ModelLayerLocation MOLTEN_ARMOR = new ModelLayerLocation(new ResourceLocation("minecraft:player"), "molten_armor");

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(MOLTEN_ARMOR, () -> LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE, false), 64, 64));
    }

    @SubscribeEvent
    public static void layerConstructor(EntityRenderersEvent.AddLayers event){
        //addLayerToHumanoid(event, EntityType.ARMOR_STAND, MoltenArmorLayer::new);
        //addLayerToHumanoid(event, EntityType.ZOMBIE, MoltenArmorLayer::new);
        //addLayerToHumanoid(event, EntityType.SKELETON, MoltenArmorLayer::new);
        //addLayerToHumanoid(event, EntityType.HUSK, MoltenArmorLayer::new);
        //addLayerToHumanoid(event, EntityType.DROWNED, MoltenArmorLayer::new);
        //addLayerToHumanoid(event, EntityType.STRAY, MoltenArmorLayer::new);

        addLayerToPlayerSkin(event, "default", MoltenArmorLayer::new);
        addLayerToPlayerSkin(event, "slim", MoltenArmorLayer::new);
    }
    private static <E extends Player, M extends HumanoidModel<E>>
    void addLayerToPlayerSkin(EntityRenderersEvent.AddLayers event, String skinName, Function<LivingEntityRenderer<E, M>, ? extends RenderLayer<E, M>> factory)
    {
        LivingEntityRenderer renderer = event.getSkin(skinName);
        if (renderer != null) renderer.addLayer(factory.apply(renderer));
    }
    private static <E extends LivingEntity, M extends HumanoidModel<E>>
    void addLayerToHumanoid(EntityRenderersEvent.AddLayers event, EntityType<E> entityType, Function<LivingEntityRenderer<E, M>, ? extends RenderLayer<E, M>> factory)
    {
        LivingEntityRenderer<E, M> renderer = event.getRenderer(entityType);
        if (renderer != null) renderer.addLayer(factory.apply(renderer));
    }
}
