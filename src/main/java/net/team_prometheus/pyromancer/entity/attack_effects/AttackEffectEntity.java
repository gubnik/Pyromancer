package net.team_prometheus.pyromancer.entity.attack_effects;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class AttackEffectEntity extends Entity {
    public boolean doesFollowPlayer;

    private static final EntityDataAccessor<Byte> AEE_ENTITY_FLAGS_ID = SynchedEntityData.defineId(AttackEffectEntity.class, EntityDataSerializers.BYTE);
    public AttackEffectEntity(EntityType<? extends AttackEffectEntity> entityType, Level level) {
        super(entityType, level);
    }
    public void setDoFollowPlayer(boolean b){
        this.doesFollowPlayer = b;
    }
    @Override
    protected void defineSynchedData(){
        this.entityData.define(AEE_ENTITY_FLAGS_ID, (byte) 0);
    }
    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag tag){
        if(tag.contains("FollowPlayer", 1)) this.setDoFollowPlayer(tag.getBoolean("FollowPlayer"));
    }
    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag tag){
        tag.putBoolean("FollowPlayer", this.doesFollowPlayer);
    }
    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
