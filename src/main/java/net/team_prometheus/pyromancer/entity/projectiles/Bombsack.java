package net.team_prometheus.pyromancer.entity.projectiles;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.team_prometheus.pyromancer.damage_source.ModDamageSource;
import net.team_prometheus.pyromancer.registries.ModEntities;
import net.team_prometheus.pyromancer.registries.ModItems;
import org.jetbrains.annotations.NotNull;

public class Bombsack extends ThrowableItemProjectile {
    public Bombsack(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(ModEntities.BOMBSACK.get(), level);
    }
    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItems.BOMBSACK.get();
    }
    private ParticleOptions getParticle() {
        ItemStack itemstack = this.getItemRaw();
        return (itemstack.isEmpty() ? ParticleTypes.SMOKE : new ItemParticleOption(ParticleTypes.ITEM, itemstack));
    }
    @Override
    public void handleEntityEvent(byte p_37402_) {
        if (p_37402_ == 3) {
            ParticleOptions particleoptions = this.getParticle();
            for(int i = 0; i < 8; ++i) {
                this.level.addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }
    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.hurt(DamageSource.thrown(this, this.getOwner()), 1);
    }
    @Override
    protected void onHit(@NotNull HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)3);
            this.discard();
            collisionEffect(this, this.level);
        }
    }
    public static void collisionEffect(Bombsack bombsack, Level level){
        level.explode(bombsack, ModDamageSource.BOMBSACK, null, bombsack.getX(), bombsack.getY(), bombsack.getZ(), 1.3f, false, Explosion.BlockInteraction.NONE);
    }
}
