package net.team_prometheus.pyromancer.worldgen.carvers;

import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;

public class ModCarvers {
    public static final DeferredRegister<ConfiguredWorldCarver<?>> CARVERS = DeferredRegister.create(Registry.CONFIGURED_CARVER_REGISTRY, PyromancerMod.MOD_ID);
    public static final RegistryObject<ConfiguredWorldCarver<?>> FLAMING_GROVE_CARVER = CARVERS.register("flaming_grove_carver",
            () -> WorldCarver.NETHER_CAVE.configured(
                    new CaveCarverConfiguration(0.2F, // probability
                            UniformHeight.of(VerticalAnchor.absolute(0), // y
                                    VerticalAnchor.belowTop(1)), // ^^
                            ConstantFloat.of(0.8F), // yScale
                            VerticalAnchor.aboveBottom(10), // lava level
                            Registry.BLOCK.getOrCreateTag(BlockTags.NETHER_CARVER_REPLACEABLES), // replaceable
                            ConstantFloat.of(1.2F), // horizontalRadiusMultiplier
                            ConstantFloat.of(1.1F), // verticalRadiusMultiplier
                            ConstantFloat.of(-0.7F)))); // floorLevel

    public static final RegistryObject<ConfiguredWorldCarver<?>> FOREST_OF_ROT = CARVERS.register("forest_of_rot_carver",
            () -> WorldCarver.NETHER_CAVE.configured(
                    new CaveCarverConfiguration(0.2F, // probability
                            UniformHeight.of(VerticalAnchor.absolute(0), // y
                                    VerticalAnchor.belowTop(1)), // ^^
                            ConstantFloat.of(0.4F), // yScale
                            VerticalAnchor.aboveBottom(10), // lava level
                            Registry.BLOCK.getOrCreateTag(BlockTags.NETHER_CARVER_REPLACEABLES), // replaceable
                            ConstantFloat.of(0.8F), // horizontalRadiusMultiplier
                            ConstantFloat.of(0.8F), // verticalRadiusMultiplier
                            ConstantFloat.of(-0.7F)))); // floorLevel
}
