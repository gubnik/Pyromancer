package net.team_prometheus.pyromancer.entity.unburned;

import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;
import net.team_prometheus.pyromancer.init.ModDamageSource;
import org.jetbrains.annotations.NotNull;

public class Unburned extends Monster {
    private final ServerBossEvent bossEvent = (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
    public AnimationState explosionAnimationState = new AnimationState();
    public AnimationState emergeAnimationState = new AnimationState();
    public AnimationState jumpingAnimationState = new AnimationState();
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState kickAnimationState = new AnimationState();
    public int countdown = 0;
    public int explosionTick = 0;
    public int attackTick = 0;
    public boolean didEmergeAnimation = false;
    public Unburned(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }
    public boolean checkSpawnObstruction(@NotNull LevelReader levelReader) {
        return super.checkSpawnObstruction(levelReader) && levelReader.noCollision(this, this.getType().getDimensions().makeBoundingBox(this.position()));
    }
    public boolean doHurtTarget(@NotNull Entity entity) {
        if(entity instanceof Player player
        && player.isBlocking()){
            this.level.broadcastEntityEvent(this, (byte) 62);
            player.disableShield(true);
            player.getCooldowns().addCooldown(
                    player.getMainHandItem().getItem(), 100);
            player.disableShield(false);
            player.setDeltaMovement(new Vec3(
                    this.getLookAngle().x*5,
                    this.getLookAngle().y*5,
                    this.getLookAngle().z*5
            ));
        } else {
            this.level.broadcastEntityEvent(this, (byte) 4);
        }
        return super.doHurtTarget(entity);
    }
    public static AttributeSupplier setAttributes(){
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 350)
                .add(Attributes.MOVEMENT_SPEED, 0.45f)
                .add(Attributes.ARMOR, 10)
                .add(Attributes.ATTACK_DAMAGE, 1)
                .add(Attributes.ATTACK_SPEED, 1)
                .add(Attributes.ATTACK_KNOCKBACK, 1.2)
                .add(Attributes.FOLLOW_RANGE, 64)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1f)
                .build();
    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new UnburnedAttackGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }
    public void handleEntityEvent(byte bt){
        //if(bt == 66){
        //    this.explosionAnimationState.stop();
        //    this.kickAnimationState.stop();
        //    this.attackAnimationState.stop();
        //    this.jumpingAnimationState.stop();
        //    this.emergeAnimationState.start(this.tickCount);
        //}
        //if(bt == 4) {
        //    this.explosionAnimationState.stop();
        //    this.kickAnimationState.stop();
        //    this.attackAnimationState.start(this.tickCount);
        //    this.attackTick = this.tickCount;
        //} else if(bt == 61) {
        //    this.attackAnimationState.stop();
        //    this.kickAnimationState.stop();
        //    this.explosionAnimationState.start(this.tickCount);
        //    this.explosionTick = this.tickCount;
        //} else if(bt == 62){
        //    this.attackAnimationState.stop();
        //    this.explosionAnimationState.stop();
        //    this.kickAnimationState.start(this.tickCount);
        //} else {
        //    super.handleEntityEvent(bt);
        //}
        switch ((int) bt) {
            case (66) -> this.emergeAnimationState.start(this.tickCount);
            case (62) -> this.kickAnimationState.start(this.tickCount);
            case (61) -> {
                this.explosionAnimationState.start(this.tickCount);
                this.explosionTick = this.tickCount;
            }
            case (4) -> {
                this.attackAnimationState.start(this.tickCount);
                this.attackTick = this.tickCount;
            }
            default -> super.handleEntityEvent(bt);
        }
    }
    public void tick(){
        super.tick();
        if(this.getTarget() != null) {
            if (this.countdown < 400) {
                this.countdown++;
            } else {
                this.countdown = 0;
                this.level.broadcastEntityEvent(this, (byte) 61);
            }
        }
        if(!this.emergeAnimationState.isStarted() && !this.didEmergeAnimation){
            this.level.broadcastEntityEvent(this, (byte) 66);
            this.didEmergeAnimation = true;
        }
        this.setInvulnerable(this.emergeAnimationState.isStarted());
        if(this.explosionAnimationState.isStarted()
        && this.explosionTick + 8 <= this.tickCount){
            explosionAttack();
            this.explosionTick = this.tickCount + 200;
        }
        else if(this.attackAnimationState.isStarted()
        && this.attackTick + 8 <= this.tickCount){
            this.level.explode(this, ModDamageSource.INCINERATION, null,
                    this.getX() + this.getLookAngle().x*2,
                    this.getY() + 2,
                    this.getZ() + this.getLookAngle().z*2,
                    0.8f, true, Explosion.BlockInteraction.NONE);
            this.attackTick = this.tickCount + 200;
        }
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }
    public void explosionAttack(){
        double x_view = this.getLookAngle().x;
        double z_view = this.getLookAngle().z;
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
       for(int  i = 0; i < 20; i++){
           this.level.explode(this, ModDamageSource.unburnedExplosion(this), null,
                    x + x_view*i,
                    y+0.1,
                    z + z_view*i,
                    1.5f, true, Explosion.BlockInteraction.NONE);
        }
    }
    static class UnburnedAttackGoal extends MeleeAttackGoal{
        public UnburnedAttackGoal(Unburned unburned){
            super(unburned, 1.4, true);
        }
        public boolean canUse(){
            if(!(this.mob instanceof Unburned unburned) || (!unburned.attackAnimationState.isStarted()
                    && !unburned.explosionAnimationState.isStarted() && !unburned.jumpingAnimationState.isStarted()
                    && !unburned.kickAnimationState.isStarted())){
                return super.canUse();
            } else {return false;}
        }
    }
    public boolean hurt(@NotNull DamageSource source, float amount){
        if(!this.emergeAnimationState.isStarted() &&
        !this.explosionAnimationState.isStarted()){
            return super.hurt(source, amount);
        } else {
            return false;
        }
    }
    public boolean removeWhenFarAway(double p_219457_) {
        return false;
    }
    public void startSeenByPlayer(@NotNull ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
    }
    public void stopSeenByPlayer(@NotNull ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }
}
