package net.team_prometheus.pyromancer;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.team_prometheus.pyromancer.worldgen.biomes.ModBiomes;
import net.team_prometheus.pyromancer.worldgen.biomes.ModNetherBiomes;
import net.team_prometheus.pyromancer.worldgen.features.ModNetherPlacements;
import net.team_prometheus.pyromancer.blocks.ModBlocks;
import net.team_prometheus.pyromancer.blocks.block_entities.ModBlockEntities;
import net.team_prometheus.pyromancer.entity.ModEntities;
import net.team_prometheus.pyromancer.entity.models.UnburnedModel;
import net.team_prometheus.pyromancer.entity.unburned.UnburnedRenderer;
import net.team_prometheus.pyromancer.init.ModAttributes;
import net.team_prometheus.pyromancer.init.ModEnchantments;
import net.team_prometheus.pyromancer.init.ModMenus;
import net.team_prometheus.pyromancer.init.ModParticleTypes;
import net.team_prometheus.pyromancer.items.ModItems;
import org.slf4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@SuppressWarnings("unused")
@Mod(PyromancerMod.MOD_ID)
public class PyromancerMod {
    public static final String MOD_ID = "pyromancer";
    public static final UUID BASE_ARMOR_TOUGHNESS_UUID = UUID.fromString("52274c52-f4c7-11ed-a05b-0242ac120003");
    public static final UUID BLAZE_CONSUMPTION_UUID = UUID.fromString("39f6d6b6-f4f9-11ed-a05b-0242ac120003");
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURE_REGISTER = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, MOD_ID);

    private static final Logger LOGGER = LogUtils.getLogger();
    public PyromancerMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEntities.ENTITIES.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBiomes.BIOMES.register(modEventBus);
        ModEnchantments.ENCHANTMENTS.register(modEventBus);
        ModAttributes.ATTRIBUTES.register(modEventBus);
        ModMenus.REGISTRY.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITY.register(modEventBus);
        ModParticleTypes.PARTICLES.register(modEventBus);
        CONFIGURED_FEATURE_REGISTER.register(modEventBus);
        ModNetherPlacements.PLACED_FEATURE_REGISTRY.register(modEventBus);
        //
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::setupClient);
        modEventBus.addListener(this::registerLayerDefinitions);
        //
        MinecraftForge.EVENT_BUS.register(this);

    }
    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModNetherBiomes::setupTerraBlender);
    }
    private void setupClient(final FMLCommonSetupEvent event) {
        EntityRenderers.register(ModEntities.UNBURNED.get(), UnburnedRenderer::new);
    }
    private void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(UnburnedModel.LAYER_LOCATION, UnburnedModel::createBodyLayer);
    }
    private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.UNBURNED.get(), UnburnedRenderer::new);
        }
    }
}
