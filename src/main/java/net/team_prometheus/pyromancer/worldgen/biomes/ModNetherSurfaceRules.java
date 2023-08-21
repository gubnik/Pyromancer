package net.team_prometheus.pyromancer.worldgen.biomes;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.team_prometheus.pyromancer.blocks.ModBlocks;

public class ModNetherSurfaceRules {
    private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    private static final SurfaceRules.RuleSource NETHERRACK = makeStateRule(Blocks.NETHERRACK);
    private static final SurfaceRules.RuleSource PYROMOSSED_NETHERRACK = makeStateRule(ModBlocks.PYROMOSSED_NETHERRACK.get());

    public static SurfaceRules.RuleSource nether() {
        SurfaceRules.ConditionSource isAbove32 = SurfaceRules.yStartCheck(VerticalAnchor.absolute(32), 0);
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(ModBiomes.FLAMING_GROVE.getKey()),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK),
                                SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())), BEDROCK),
                                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                        SurfaceRules.ifTrue(isAbove32, PYROMOSSED_NETHERRACK)),
                                SurfaceRules.ifTrue(SurfaceRules.UNDER_CEILING, NETHERRACK),
                                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, NETHERRACK)
                        )
                )
        );
    }
    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
