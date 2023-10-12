package net.team_prometheus.pyromancer.entity.projectiles;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.team_prometheus.pyromancer.entity.EntityUtils;
import net.team_prometheus.pyromancer.damage_source.ModDamageSource;
import net.team_prometheus.pyromancer.init.PlaySound;
import org.jetbrains.annotations.NotNull;

public class SizzlingHandFireball extends Fireball implements ItemSupplier {
    public int maxLifetime = 0;
    public float damage = 0;
    public SizzlingHandFireball(EntityType<? extends Fireball> fireball, Level level) {
        super(fireball, level);
    }
    public SizzlingHandFireball setParameters(float damage, int maxLifetime){
        this.damage = damage;
        this.maxLifetime = maxLifetime;
        return this;
    }
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (!this.level.isClientSide) {
            Entity entity = entityHitResult.getEntity();
            Entity owner = this.getOwner();
            int i = entity.getRemainingFireTicks();
            entity.setSecondsOnFire(5);
            collisionEffect(this, this.getOwner(), entity.level);
            entity.hurt(ModDamageSource.sizzlingHand(owner), damage);
            if (!entity.hurt(ModDamageSource.sizzlingHand(owner), damage)) {
                entity.setRemainingFireTicks(i);
            } else if (owner instanceof LivingEntity) {
                this.doEnchantDamageEffects((LivingEntity)owner, entity);
            }
        }
    }
    @Override
    protected void onHitBlock(@NotNull BlockHitResult result){
        collisionEffect(this, this.getOwner(), this.level);
    }
    @Override
    public void tick() {
        if(this.tickCount > this.maxLifetime && !this.level.isClientSide) {
            collisionEffect(this, this.getOwner(), this.level);
            this.remove(RemovalReason.DISCARDED);
        }
        super.tick();
    }
    protected boolean shouldBurn() {
        return true;
    }
    protected @NotNull ParticleOptions getTrailParticle() {
        return ParticleTypes.SMOKE;
    }
    @Override
    protected float getInertia() {
        return 1f;
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
    public static void collisionEffect(SizzlingHandFireball fireball, Entity owner, Level level) {
        double x = fireball.getX();
        double y = fireball.getY();
        double z = fireball.getZ();
        PlaySound.run("entity.generic.explode", SoundSource.NEUTRAL, 1f, 1f, level, x, y, z, null);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.FLAME, x, y, z, 20, 0.25, 0.25, 0.25, 0.2);
            serverLevel.sendParticles(ParticleTypes.LAVA, x, y, z, 10, 0.25, 0.25, 0.25, 0.1);
            Vec3 center = new Vec3(x, y, z);
            for (Entity entityiterator : EntityUtils.entityCollector(center, 2, fireball.level)) {
                if (!(fireball.getOwner() == entityiterator)) {
                    entityiterator.hurt(ModDamageSource.sizzlingHand(owner), fireball.damage);
                }
            }
        }
    }
}