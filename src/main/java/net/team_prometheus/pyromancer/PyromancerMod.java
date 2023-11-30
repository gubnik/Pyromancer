package net.team_prometheus.pyromancer;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.items.embers.Ember;
import net.team_prometheus.pyromancer.items.weaponary.cryomancy.CryomancyItem;
import net.team_prometheus.pyromancer.items.weaponary.pyromancy.PyromancyItem;
import net.team_prometheus.pyromancer.registries.ModBlocks;
import net.team_prometheus.pyromancer.blocks.block_entities.ModBlockEntities;
import net.team_prometheus.pyromancer.registries.ModEnchantments;
import net.team_prometheus.pyromancer.registries.ModEntities;
import net.team_prometheus.pyromancer.entity.models.HeavenlyFlameModel;
import net.team_prometheus.pyromancer.entity.models.PyromancerArmorModel;
import net.team_prometheus.pyromancer.entity.models.UnburnedModel;
import net.team_prometheus.pyromancer.entity.renderers.FirePillarRenderer;
import net.team_prometheus.pyromancer.entity.renderers.UnburnedRenderer;
import net.team_prometheus.pyromancer.registries.ModAttributes;
import net.team_prometheus.pyromancer.registries.ModParticleTypes;
import net.team_prometheus.pyromancer.registries.ModItems;
import net.team_prometheus.pyromancer.registries.ModMobEffects;
import net.team_prometheus.pyromancer.network.NetworkCore;
import net.team_prometheus.pyromancer.registries.ModBiomes;
import net.team_prometheus.pyromancer.util.GeneralUtils;
import net.team_prometheus.pyromancer.worldgen.biomes.ModNetherBiomes;
import net.team_prometheus.pyromancer.registries.ModCarvers;
import net.team_prometheus.pyromancer.registries.ModNetherPlacements;
import net.team_prometheus.pyromancer.registries.ModFoliageTypes;
import net.team_prometheus.pyromancer.registries.ModTrunkTypes;
import org.slf4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import static net.minecraft.world.item.ItemStack.ATTRIBUTE_MODIFIER_FORMAT;

@SuppressWarnings("unused")
@Mod(PyromancerMod.MOD_ID)
public class PyromancerMod {
    public static final String MOD_ID = "pyromancer";
    public static final UUID BASE_ARMOR_TOUGHNESS_UUID = UUID.fromString("52274c52-f4c7-11ed-a05b-0242ac120003");
    public static final UUID BASE_BLAZE_CONSUMPTION_UUID = UUID.fromString("39f6d6b6-f4f9-11ed-a05b-0242ac120003");
    public static final UUID BASE_PYROMANCY_DAMAGE_UUID = UUID.fromString("4ec062f8-14ff-11ee-be56-0242ac120002");
    public static final UUID JOURNAL_BLAZE_CONSUMPTION_UUID = UUID.fromString("574d4092-16c3-11ee-be56-0242ac120002");
    public static final UUID JOURNAL_PYROMANCY_DAMAGE_UUID = UUID.fromString("704049d2-16c3-11ee-be56-0242ac120002");
    public static final UUID MACE_CC_TOUGHNESS_UUID = UUID.fromString("0dbcb61c-1e64-11ee-be56-0242ac120002");
    public static final UUID MACE_CC_RANGE_MODIFIER_UUID = UUID.fromString("2df56af0-1e64-11ee-be56-0242ac120002");
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURE_REGISTER = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, MOD_ID);

    public static final Logger LOGGER = LogUtils.getLogger();
    public PyromancerMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, PyromancerConfig.COMMON_SPEC, "pyromancer/common.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, PyromancerConfig.CLIENT_SPEC, "pyromancer/client.toml");

        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModEntities.ENTITIES.register(modEventBus);
        ModMobEffects.EFFECTS.register(modEventBus);
        ModBiomes.BIOMES.register(modEventBus);
        ModEnchantments.ENCHANTMENTS.register(modEventBus);
        ModAttributes.ATTRIBUTES.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITY.register(modEventBus);
        ModParticleTypes.PARTICLES.register(modEventBus);
        ModTrunkTypes.TRUNK_TYPE_REGISTRY.register(modEventBus);
        ModFoliageTypes.FOLIAGE_PLACER_REGISTRY.register(modEventBus);
        CONFIGURED_FEATURE_REGISTER.register(modEventBus);
        ModNetherPlacements.PLACED_FEATURE_REGISTRY.register(modEventBus);
        ModCarvers.CARVERS.register(modEventBus);
        //
        modEventBus.addListener(this::registerPackets);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::setupClient);
        modEventBus.addListener(this::registerLayerDefinitions);
        //
        MinecraftForge.EVENT_BUS.register(this);
    }
    private void registerPackets(FMLCommonSetupEvent event){
        NetworkCore.register();
    }
    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModNetherBiomes::setupTerraBlender);
    }
    private void setupClient(final FMLCommonSetupEvent event) {
        EntityRenderers.register(ModEntities.UNBURNED.get(), UnburnedRenderer::new);
        EntityRenderers.register(ModEntities.FIRE_PILLAR.get(), FirePillarRenderer::new);
    }
    private void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(UnburnedModel.LAYER_LOCATION, UnburnedModel::createBodyLayer);
        event.registerLayerDefinition(HeavenlyFlameModel.LAYER_LOCATION, HeavenlyFlameModel::createBodyLayer);
        event.registerLayerDefinition(PyromancerArmorModel.LAYER_LOCATION, PyromancerArmorModel::createBodyLayer);
    }
    private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();
    public static void queueServerWork(int tick, Runnable action) {
        workQueue.add(new AbstractMap.SimpleEntry<>(action, tick));
    }
    @SubscribeEvent
    public void tick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            List<AbstractMap.SimpleEntry<Runnable, Integer>> actions = new ArrayList<>();
            workQueue.forEach(work -> {
                work.setValue(work.getValue() - 1);
                if (work.getValue() == 0)
                    actions.add(work);
            });
            actions.forEach(e -> e.getKey().run());
            workQueue.removeAll(actions);
        }
    }
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void tooltipColoringEvent(RenderTooltipEvent.Color event){
            if(event.getItemStack().getItem() instanceof PyromancyItem) {
                event.setBackgroundStart(
                        GeneralUtils.rgbaToColorInteger(134, 54, 0, 255)
                );
                event.setBackgroundEnd(
                        GeneralUtils.rgbaToColorInteger(100, 44, 0, 196)
                );
                event.setBorderStart(GeneralUtils.rgbaToColorInteger(240, 220, 0, 255));
                return;
            }
            if(event.getItemStack().getItem() instanceof CryomancyItem) {
                event.setBackgroundStart(
                        GeneralUtils.rgbaToColorInteger(0, 44, 124, 255)
                );
                event.setBackgroundEnd(
                        GeneralUtils.rgbaToColorInteger(0, 12, 100, 196)
                );
                event.setBorderStart(GeneralUtils.rgbaToColorInteger(0, 255, 190, 255));
                return;
            }
            if(event.getItemStack().getItem() instanceof TieredItem
            && Ember.isValidEmber(event.getItemStack().getOrCreateTag().getString("ember"))) {
                String emberTag = event.getItemStack().getOrCreateTag().getString("ember");
                int color = 0;
                color = switch (Ember.byName(emberTag).getInfusionType()){
                    case FLAME -> GeneralUtils.rgbaToColorInteger(134, 64, 0, 196);
                    case HELLBLAZE ->  GeneralUtils.rgbaToColorInteger(154, 32, 0, 196);
                    case SOULFLAME -> GeneralUtils.rgbaToColorInteger(16, 172, 164, 196);
                };
                if (color == 0) return;
                event.setBackgroundStart(color);
                //event.setBorderStart(GeneralUtils.rgbaToColorInteger(196, 240, 0, 255));
                return;
            }
        }
        @SubscribeEvent
        public static void tooltipLineEvent(ItemTooltipEvent event) {
            if(!(event.getItemStack().getItem() instanceof PyromancyItem pyromancyItem)) return;
            List<Pair<String, Integer>> searchList = new ArrayList<>();
            List<Component> forModification = List.copyOf(event.getToolTip());
            List<Component> forDeletion = new ArrayList<>();

            searchList.add(Pair.of(Component.translatable("attribute.generic.blaze_consumption").getString(), pyromancyItem.blazeConsumptionModifier));
            searchList.add(Pair.of(Component.translatable("attribute.generic.pyromancy_damage").getString(), pyromancyItem.pyromancyDamageModifier));

            for (Component component : forModification)
            {
                for(Pair<String, Integer> s : searchList)
                {
                    LOGGER.warn(s.getFirst());
                    if (component.getString().contains(s.getFirst()) && component.getString().contains("+"))
                    {
                        forDeletion.add(component);
                        event.getToolTip().add(Component.literal(" ")
                                .append(Component.translatable("attribute.modifier.equals." + 0,
                                        ATTRIBUTE_MODIFIER_FORMAT.format(s.getSecond()),
                                        Component.translatable(s.getFirst()))
                                ).withStyle(ChatFormatting.GOLD));
                    }
                }
            }
            for(Component component : forDeletion) event.getToolTip().remove(component);
        }
    }
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.UNBURNED.get(), UnburnedRenderer::new);
        }
    }
}
