package net.team_prometheus.pyromancer.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.team_prometheus.pyromancer.registries.ModItems;
import org.jetbrains.annotations.NotNull;

public class PyromancerChestBoatEntity extends ChestBoat {
    public PyromancerChestBoatEntity(EntityType<? extends Boat> p_219869_, Level p_219870_) {
        super(p_219869_, p_219870_);
    }

    public PyromancerChestBoatEntity(Level p_219872_, double p_219873_, double p_219874_, double p_219875_) {
        super(p_219872_, p_219873_, p_219874_, p_219875_);
    }
    @Override
    public @NotNull Packet<?> getAddEntityPacket()
    {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt)
    {
        nbt.putString("model", getModel().getName());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt)
    {
        if (nbt.contains("model", Tag.TAG_STRING))
        {
            this.entityData.set(DATA_ID_TYPE, PyromancerBoatEntity.ModelType.byName(nbt.getString("model")).ordinal());
        }
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos)
    {
        this.lastYd = this.getDeltaMovement().y;
        if (!this.isPassenger())
        {
            if (onGround)
            {
                if (this.fallDistance > 3.0F)
                {
                    if (this.status != Status.ON_LAND)
                    {
                        this.resetFallDistance();
                        return;
                    }

                    this.causeFallDamage(this.fallDistance, 1.0F, DamageSource.FALL);
                    if (!this.level.isClientSide && !this.isRemoved())
                    {
                        this.kill();
                        if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS))
                        {
                            for (int i = 0; i < 3; ++i)
                            {
                                this.spawnAtLocation(this.getModel().getPlanks());
                            }

                            for (int j = 0; j < 2; ++j)
                            {
                                this.spawnAtLocation(Items.STICK);
                            }
                        }
                    }
                }

                this.resetFallDistance();
            }
            else if (!this.level.getFluidState(this.blockPosition().below()).is(FluidTags.WATER) && y < 0.0D)
            {
                this.fallDistance -= (float)y;
            }
        }
    }

    @Override
    public @NotNull Item getDropItem()
    {
        switch (PyromancerBoatEntity.ModelType.byId(this.entityData.get(DATA_ID_TYPE)))
        {
            case PYROWOOD:
                return ModItems.PYROWOOD_BOAT.get();
        }
        return Items.OAK_BOAT;
    }

    public void setModel(PyromancerBoatEntity.ModelType type)
    {
        this.entityData.set(DATA_ID_TYPE, type.ordinal());
    }

    public PyromancerBoatEntity.ModelType getModel()
    {
        return PyromancerBoatEntity.ModelType.byId(this.entityData.get(DATA_ID_TYPE));
    }

    @Deprecated
    @Override
    public void setType(@NotNull Type vanillaType) {}

    @Deprecated
    @Override
    public @NotNull Type getBoatType()
    {
        return Type.OAK;
    }
}
