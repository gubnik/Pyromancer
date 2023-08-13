package net.team_prometheus.pyromancer.worldgen.features;

import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.blocks.ModBlocks;
import net.team_prometheus.pyromancer.worldgen.trees.trunks.NetherPyrowoodTrunkPlacer;

import java.util.function.Supplier;

public class ModFeatures {
    public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> PYROWOOD_NETHER = register("pyrowood_nether", Feature.TREE,
            () -> (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.PYROWOOD_LOG.get()), new NetherPyrowoodTrunkPlacer(7, 3, 1),
                    BlockStateProvider.simple(ModBlocks.PYROWOOD_LEAVES.get()),
                    new RandomSpreadFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), ConstantInt.of(3), 24),
                    new TwoLayersFeatureSize(1, 0, 0))).dirt(BlockStateProvider.simple(Blocks.NETHERRACK)).build());
    public static final WeightedStateProvider FLAMING_GROVE_PROVIDER = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
            .add(ModBlocks.PYROMOSS_SPROUTS.get().defaultBlockState(), 87)
            .add(ModBlocks.FIREBRIAR.get().defaultBlockState(), 13)
    );
    public static final RegistryObject<ConfiguredFeature<NetherForestVegetationConfig, ?>> FLAMING_GROVE_VEGETATION = register("flaming_grove_vegetation",
            Feature.NETHER_FOREST_VEGETATION, () -> new NetherForestVegetationConfig(FLAMING_GROVE_PROVIDER, 8, 4));
    public static <FC extends FeatureConfiguration, F extends Feature<FC>> RegistryObject<ConfiguredFeature<FC, ?>> register(String key, F feature, Supplier<FC> configurationSupplier)
    {
        return PyromancerMod.CONFIGURED_FEATURE_REGISTER.register(key, () -> new ConfiguredFeature<>(feature, configurationSupplier.get()));
    }
}
