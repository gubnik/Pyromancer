package net.team_prometheus.pyromancer.entity.projectiles;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.team_prometheus.pyromancer.damage_source.ModDamageSource;
import org.jetbrains.annotations.NotNull;

public class SparkProjectile  extends Fireball implements ItemSupplier {
    public int maxLifetime = 0;
    public float damage = 0;
    public SparkProjectile(EntityType<? extends Fireball> entityType, Level level) {
        super(entityType, level);
    }
    public SparkProjectile setParameters(float damage, int maxLifetime){
        this.damage = damage;
        this.maxLifetime = maxLifetime;
        return this;
    }
    @Override
    public void tick() {
        if(this.tickCount > this.maxLifetime && !this.level.isClientSide) {
            this.remove(RemovalReason.DISCARDED);
        }
        super.tick();
    }
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (!this.level.isClientSide) {
            Entity entity = entityHitResult.getEntity();
            Entity owner = this.getOwner();
            int i = entity.getRemainingFireTicks();
            entity.setSecondsOnFire(2);
            entity.hurt(ModDamageSource.spark(owner), damage);
            if (!entity.hurt(ModDamageSource.spark(owner), damage)) {
                entity.setRemainingFireTicks(i);
            } else if (owner instanceof LivingEntity) {
                this.doEnchantDamageEffects((LivingEntity)owner, entity);
            }
        }
    }
}
