package net.team_prometheus.pyromancer.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockState;

public class BlocksUtils {
    public static boolean flamingGrovePlantable(BlockState blockState){
        return blockState.is(BlockTags.create(new ResourceLocation("forge:flaming_grove_plant_placeable")));
    }
}
