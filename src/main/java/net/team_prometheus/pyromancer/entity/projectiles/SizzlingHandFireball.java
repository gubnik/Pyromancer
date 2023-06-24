package net.team_prometheus.pyromancer.entity.projectiles;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.team_prometheus.pyromancer.entity.ModEntities;
import net.team_prometheus.pyromancer.init.ModDamageSource;
import net.team_prometheus.pyromancer.init.PlaySound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Comparator;
import java.util.List;

public class SizzlingHandFireball extends Fireball implements ItemSupplier {
    public int lifetime;
    public SizzlingHandFireball(PlayMessages.SpawnEntity packet, Level world) {
        super(ModEntities.SIZZLING_HAND_FIREBALL.get(), world);
    }
    public SizzlingHandFireball(EntityType<? extends Fireball> fireball, Level level) {
        super(fireball, level);
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
            collisionEffect(this, entity, entity.level);
            entity.hurt(ModDamageSource.sizzlingHandFireball(this, entity), 3.0F);
            if (!entity.hurt(ModDamageSource.sizzlingHandFireball(this, entity), 3.0F)) {
                entity.setRemainingFireTicks(i);
            } else if (owner instanceof LivingEntity) {
                this.doEnchantDamageEffects((LivingEntity)owner, entity);
            }
        }
    }
    @Override
    protected void onHitBlock(@NotNull BlockHitResult result){
        collisionEffect(this, null, this.level);
    }
    @Override
    public void tick() {
        this.lifetime+=1;
        if(lifetime >= 10){
            collisionEffect(this, null, level);
            this.discard();
        }
        Entity entity = this.getOwner();
        if (this.level.isClientSide || (entity == null || !entity.isRemoved()) && this.level.hasChunkAt(this.blockPosition())) {
            super.tick();
            if (this.shouldBurn()) {
                this.setSecondsOnFire(1);
            }
            HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
            if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
            }
            this.checkInsideBlocks();
            Vec3 vec3 = this.getDeltaMovement();
            double d0 = this.getX() + vec3.x;
            double d1 = this.getY() + vec3.y;
            double d2 = this.getZ() + vec3.z;
            ProjectileUtil.rotateTowardsMovement(this, 0.2F);
            float f = this.getInertia();
            if (this.isInWater()) {
                for(int i = 0; i < 4; ++i) {
                    float f1 = 0.25F;
                    this.level.addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * f1, d1 - vec3.y * f1, d2 - vec3.z * f1, vec3.x, vec3.y, vec3.z);
                }
                f = 0.8F;
            }
            this.setDeltaMovement(vec3.add(this.xPower, this.yPower, this.zPower).scale(f));
            this.level.addParticle(this.getTrailParticle(), d0, d1 + 0.5D, d2, 0.0D, 0.0D, 0.0D);
            this.setPos(d0, d1, d2);
        } else {
            this.discard();
        }
    }
    @Override
    protected @NotNull ParticleOptions getTrailParticle() {
        return ParticleTypes.LARGE_SMOKE;
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
    @Override
    protected void defineSynchedData() {
    }
    public static void collisionEffect(SizzlingHandFireball fireball, @Nullable Entity entity, Level level) {
        double x = fireball.getX();
        double y = fireball.getY();
        double z = fireball.getZ();
        PlaySound.run("entity.generic.explode", SoundSource.NEUTRAL, 1f, 1f, level, x, y, z, null);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.FLAME, x, y, z, 20, 0.25, 0.25, 0.25, 0.2);
            serverLevel.sendParticles(ParticleTypes.LAVA, x, y, z, 10, 0.25, 0.25, 0.25, 0.1);
            Vec3 _center = new Vec3(x, y, z);
            List<Entity> _entfound = serverLevel.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(entComp -> entComp.distanceToSqr(_center))).toList();
            for (Entity entityiterator : _entfound) {
                if (!(fireball.getOwner() == entityiterator)) {
                    entityiterator.hurt(DamageSource.IN_FIRE.bypassInvul(), 5);
                }
            }
        }
    }
}