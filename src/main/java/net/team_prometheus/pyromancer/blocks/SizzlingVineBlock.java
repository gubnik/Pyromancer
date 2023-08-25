package net.team_prometheus.pyromancer.blocks;

import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;

public class SizzlingVineBlock extends GrowingPlantHeadBlock implements BonemealableBlock {
    public static final BooleanProperty THICK = BooleanProperty.create("thick");
    protected SizzlingVineBlock(Properties properties) {
        super(properties, Direction.DOWN,
                Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D),
                false, 0.05);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(THICK, false));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(THICK);
    }
    @Override
    protected int getBlocksToGrowWhenBonemealed(@NotNull RandomSource randomSource) {
        return 0;
    }
    @Override
    protected boolean canGrowInto(@NotNull BlockState blockState) {
        return false;
    }
    @Override
    protected @NotNull Block getBodyBlock() {
        return ModBlocks.SIZZLING_VINE.get();
    }
}
