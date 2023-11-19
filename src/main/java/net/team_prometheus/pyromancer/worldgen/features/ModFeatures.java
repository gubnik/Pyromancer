package net.team_prometheus.pyromancer.worldgen.features;

import net.minecraft.core.Direction;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.blocks.ModBlocks;
import net.team_prometheus.pyromancer.blocks.SizzlingVineBlock;
import net.team_prometheus.pyromancer.worldgen.trees.trunks.NetherPyrowoodTrunkPlacer;

import java.util.List;
import java.util.function.Supplier;

public class ModFeatures {
    public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> PYROWOOD_NETHER = register("pyrowood_nether", Feature.TREE,
            () -> (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.PYROWOOD_LOG.get()), new NetherPyrowoodTrunkPlacer(7, 3, 1),
                    BlockStateProvider.simple(ModBlocks.PYROWOOD_LEAVES.get()),
                    new RandomSpreadFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), ConstantInt.of(3), 24),
                    new TwoLayersFeatureSize(1, 0, 0))).dirt(BlockStateProvider.simple(Blocks.NETHERRACK)).build());
    public static final RegistryObject<ConfiguredFeature<NetherForestVegetationConfig, ?>> FLAMING_GROVE_VEGETATION = register("flaming_grove_vegetation",
            Feature.NETHER_FOREST_VEGETATION, () -> new NetherForestVegetationConfig(
                    new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                    .add(ModBlocks.PYROMOSS_SPROUTS.get().defaultBlockState(), 50)
                    .add(ModBlocks.FIREBRIAR.get().defaultBlockState(), 13)
                    .add(ModBlocks.BLAZING_POPPY.get().defaultBlockState(), 13)
                    .add(ModBlocks.NETHER_LILY.get().defaultBlockState(), 11)
                    .add(ModBlocks.PYROWOOD_SAPLING.get().defaultBlockState(), 3))
                    , 32, 16));
    public static final RegistryObject<ConfiguredFeature<BlockColumnConfiguration, ?>> SIZZLING_VINE = register("sizzling_vines",
            Feature.BLOCK_COLUMN, ()-> new BlockColumnConfiguration(
                    List.of(BlockColumnConfiguration.layer(
                            new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                    .add(UniformInt.of(0, 19), 2)
                                    .add(UniformInt.of(0, 2), 3)
                                    .add(UniformInt.of(0, 6), 10).build()),
                                    // SIZZLING_VINE_BODY_PROVIDER
                                    new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                            .add(ModBlocks.SIZZLING_VINE.get().defaultBlockState(), 4)
                                            .add(ModBlocks.SIZZLING_VINE.get().defaultBlockState().setValue(SizzlingVineBlock.THICK, Boolean.TRUE), 1))),
            BlockColumnConfiguration.layer(ConstantInt.of(1),
                    // SIZZLING_VINE_HEAD_PROVIDER
                    new RandomizedIntStateProvider(
                            new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                    .add(ModBlocks.SIZZLING_VINE.get().defaultBlockState(), 4)
                                    .add(ModBlocks.SIZZLING_VINE.get().defaultBlockState().setValue(SizzlingVineBlock.THICK, Boolean.TRUE), 1)), SizzlingVineBlock.AGE, UniformInt.of(23, 25))
            )), Direction.DOWN, BlockPredicate.ONLY_IN_AIR_PREDICATE, true));
    public static <FC extends FeatureConfiguration, F extends Feature<FC>> RegistryObject<ConfiguredFeature<FC, ?>> register(String key, F feature, Supplier<FC> configurationSupplier)
    {
        return PyromancerMod.CONFIGURED_FEATURE_REGISTER.register(key, () -> new ConfiguredFeature<>(feature, configurationSupplier.get()));
    }
}
