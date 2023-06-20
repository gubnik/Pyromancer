package net.team_prometheus.pyromancer.worldgen.features;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.blocks.ModBlocks;

import java.util.List;
import java.util.function.Supplier;
@SuppressWarnings("deprecation")
public class ModNetherPlacements {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURE_REGISTRY = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, PyromancerMod.MOD_ID);
    public static final RegistryObject<PlacedFeature> PYROWOOD_NETHER = register("pyrowood_nether_placement",
            ModFeatures.PYROWOOD_NETHER, () ->  List.of(CountOnEveryLayerPlacement.of(10), PlacementUtils.filteredByBlockSurvival(ModBlocks.PYROMOSS_SPROUTS.get()), BiomeFilter.biome()));
    public static RegistryObject<PlacedFeature> PYROMOSS_SPROUTS_PLACEMENT = register("pyromoss_sprouts_placement",
            ModFeatures.PYROMOSS_SPROUTS, () -> List.of(CountOnEveryLayerPlacement.of(6), BiomeFilter.biome()));
    public static RegistryObject<PlacedFeature> register(String key, RegistryObject<? extends ConfiguredFeature<?, ?>> feature, Supplier<List<PlacementModifier>> modifiers) {
        return PLACED_FEATURE_REGISTRY.register(key, () -> new PlacedFeature(Holder.hackyErase(feature.getHolder().orElseThrow()), List.copyOf(modifiers.get())));
    }
    public static RegistryObject<PlacedFeature> register(String key, RegistryObject<? extends ConfiguredFeature<?, ?>> feature) {
        return register(key, feature, List::of);
    }
}
