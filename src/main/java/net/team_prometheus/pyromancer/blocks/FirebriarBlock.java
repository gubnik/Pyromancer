package net.team_prometheus.pyromancer.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class FirebriarBlock extends FlowerBlock {
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 4);
    public FirebriarBlock(Properties p_53514_) {
        super(MobEffects.FIRE_RESISTANCE, 100, p_53514_);
        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, 0));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }
    protected boolean mayPlaceOn(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
        return BlocksUtil.flamingGrovePlantable(blockState);
    }
    @Override
    public void entityInside(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull Entity entity){
        double x = blockPos.getX();
        double y = blockPos.getY();
        double z = blockPos.getZ();
        super.entityInside(blockState, level, blockPos, entity);
        if(blockState.getBlock().getStateDefinition().getProperty("stage") instanceof IntegerProperty stage && blockState.getValue(stage) == 0){
            level.setBlock(blockPos, blockState.setValue(stage, 3), 3); // texture change
            level.explode(null, DamageSource.explosion((LivingEntity) null), null, x+0.5, y, z+0.5, 0.8f, true, Explosion.BlockInteraction.NONE);
        }
    }
    @Override
    public void animateTick(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource){
        super.animateTick(blockState, level, blockPos, randomSource);
        if(blockState.getBlock().getStateDefinition().getProperty("stage") instanceof IntegerProperty stage && blockState.getValue(stage) > 0
        && Math.random() < 0.08){
            level.setBlock(blockPos, blockState.setValue(stage,
                    blockState.getValue(stage) - 1), 3);
        }
    }
}