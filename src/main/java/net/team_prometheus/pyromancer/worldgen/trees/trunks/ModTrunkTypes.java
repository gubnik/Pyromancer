package net.team_prometheus.pyromancer.worldgen.trees.trunks;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;

public class ModTrunkTypes {
    //public static TrunkPlacerType<NetherPyrowoodTrunkPlacer> NETHER_PYROWOOD_TRUNK_PLACER; //= TrunkPlacerType.register("nether_pyrowood_trunk_placer", NetherPyrowoodTrunkPlacer.CODEC);
    //public static void setupTypes(){
    //    NETHER_PYROWOOD_TRUNK_PLACER = TrunkPlacerType.register("nether_pyrowood_trunk_placer", NetherPyrowoodTrunkPlacer.CODEC);
    //}

    public static DeferredRegister<TrunkPlacerType<?>> TRUNK_TYPE_REGISTRY = DeferredRegister.create(Registry.TRUNK_PLACER_TYPE_REGISTRY, PyromancerMod.MOD_ID);
    public static final RegistryObject<TrunkPlacerType<?>> NETHER_PYROWOOD_TRUNK_PLACER = TRUNK_TYPE_REGISTRY.register("nether_pyrowood_trunk_placer",
            ()-> new TrunkPlacerType<>(NetherPyrowoodTrunkPlacer.CODEC));
}
