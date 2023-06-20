package net.team_prometheus.pyromancer.worldgen.tree_growers;

import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.team_prometheus.pyromancer.worldgen.features.ModFeatures;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PyrowoodTreeGrower extends AbstractTreeGrower {
    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource source, boolean check) {
        return ModFeatures.PYROWOOD_NETHER.getHolder().get();
    }
}
