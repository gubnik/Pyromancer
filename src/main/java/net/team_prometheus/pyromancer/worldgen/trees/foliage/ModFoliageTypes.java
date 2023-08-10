package net.team_prometheus.pyromancer.worldgen.trees.foliage;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;

public class ModFoliageTypes {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_REGISTRY = DeferredRegister.create(Registry.FOLIAGE_PLACER_TYPE_REGISTRY, PyromancerMod.MOD_ID);
    public static final RegistryObject<FoliagePlacerType<?>> NETHER_PYROWOOD_FOLIAGE_PLACER = FOLIAGE_PLACER_REGISTRY.register("nether_pyrowood_foliage_placer",
            ()-> new FoliagePlacerType<>(NetherPyrowoodFoliagePlacer.CODEC));
}
