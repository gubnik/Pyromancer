package net.team_prometheus.pyromancer.entity.projectiles;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import net.team_prometheus.pyromancer.entity.ModEntities;
import net.team_prometheus.pyromancer.init.PlaySound;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class SizzlingHandFireball extends Fireball implements ItemSupplier {
    public SizzlingHandFireball(PlayMessages.SpawnEntity packet, Level world) {
        super(ModEntities.SIZZLING_HAND_FIREBALL.get(), world);
    }

    public SizzlingHandFireball(EntityType<? extends Fireball> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }
    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
    @Override
    @OnlyIn(Dist.CLIENT)
    public @NotNull ItemStack getItem() {
        return new ItemStack(Items.FIRE_CHARGE);
    }
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (!this.level.isClientSide) {
            Entity entity = entityHitResult.getEntity();
            Entity owner = this.getOwner();
            int i = entity.getRemainingFireTicks();
            entity.setSecondsOnFire(5);
            collisionEffect(owner, entity, entity.level);
            if (!entity.hurt(DamageSource.fireball(this, owner), 5.0F)) {
                entity.setRemainingFireTicks(i);
            } else if (owner instanceof LivingEntity) {
                this.doEnchantDamageEffects((LivingEntity)owner, entity);
            }
        }
    }
    public static void collisionEffect(Entity owner, Entity entity, Level level) {
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        PlaySound.run("entity.generic.explode", SoundSource.NEUTRAL, 1f, 1f, level, x, y, z, null);
        if(level instanceof ServerLevel serverLevel){
            serverLevel.sendParticles(ParticleTypes.FLAME, x, y, z, 50, 0.5, 0.5, 0.5, 1);
            serverLevel.sendParticles(ParticleTypes.LAVA, x, y, z, 20, 0.5, 0.5, 0.5, 0.5);
        }
        Vec3 _center = new Vec3(x, y, z);
        List<Entity> _entfound = level.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(entComp -> entComp.distanceToSqr(_center))).toList();
        for (Entity entityiterator : _entfound) {
            if (!(owner == entityiterator)) {
                entityiterator.hurt(DamageSource.IN_FIRE, 6);
            }
        }
    }
    @Override
    protected @NotNull ParticleOptions getTrailParticle() {
        return ParticleTypes.LARGE_SMOKE;
    }
    @Override
    protected float getInertia() {
        return 1;
    }
    @Override
    protected boolean canHitEntity(@NotNull Entity entity) {
        return super.canHitEntity(entity) && !entity.noPhysics;
    }
    @Override
    protected void onHit(@NotNull HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level.isClientSide) {
            this.discard();
        }
    }
    @Override
    public boolean hurt(@NotNull DamageSource damageSource, float p_36840_) {
        return false;
    }
    @Override
    protected void defineSynchedData() {
    }
}

