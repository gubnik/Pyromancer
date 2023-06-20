package net.team_prometheus.pyromancer.worldgen.features;

import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.blocks.ModBlocks;

import java.util.function.Supplier;

public class ModFeatures {
    public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> PYROWOOD_NETHER = register("pyrowood_nether", Feature.TREE,
            () -> (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.PYROWOOD_LOG.get()), new ForkingTrunkPlacer(6, 3, 1),
                    BlockStateProvider.simple(ModBlocks.PYROWOOD_LEAVES.get()), new FancyFoliagePlacer(ConstantInt.of(2),
                    ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 0))).dirt(BlockStateProvider.simple(Blocks.NETHERRACK)).build());
    public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> PYROMOSS_SPROUTS = register("flaming_grove_vegetation", Feature.RANDOM_PATCH,
            () -> (FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.PYROMOSS_SPROUTS.get())))));
    public static <FC extends FeatureConfiguration, F extends Feature<FC>> RegistryObject<ConfiguredFeature<FC, ?>> register(String key, F feature, Supplier<FC> configurationSupplier)
    {
        return PyromancerMod.CONFIGURED_FEATURE_REGISTER.register(key, () -> new ConfiguredFeature<>(feature, configurationSupplier.get()));
    }
}
