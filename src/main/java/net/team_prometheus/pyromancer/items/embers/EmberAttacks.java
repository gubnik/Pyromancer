package net.team_prometheus.pyromancer.items.embers;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.team_prometheus.pyromancer.util.EntityUtils;
import net.team_prometheus.pyromancer.registries.ModEntities;
import net.team_prometheus.pyromancer.entity.attack_effects.FirePillarEntity;
import net.team_prometheus.pyromancer.damage_source.ModDamageSource;
import net.team_prometheus.pyromancer.registries.ModMobEffects;

import java.util.Objects;


@Mod.EventBusSubscriber
public class EmberAttacks {
    public static void soulflameIgnition(Player player){
        Ember ember = Ember.SOULFLAME_IGNITION;
        float damage = ember.getDamage();
        //
        double x = player.getX();
        double y = player.getY() + 1.5;
        double z = player.getZ();
        //
        double lx = player.getLookAngle().x;
        double ly = player.getLookAngle().y;
        double lz = player.getLookAngle().z;
        //
        if(player.level instanceof ServerLevel level) {
            for(int a = 0; a < 4; a++){
                double dx = 0;
                double dy = 0;
                double dz = 0;
                double k = 0;
                for (int i = 0; i < 120; i++) {
                    double xx = x + lx * k + dx;
                    double yy = y + ly * k + dy;
                    double zz = z + lz * k + dz;
                    level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                            xx, yy, zz, 1, 0, 0, 0, 0.02);
                    for (LivingEntity entity : EntityUtils.entityCollector(new Vec3(xx, yy, zz), 2, level)){
                        if (!entity.equals(player) && !(entity.hurtTime > 0)) {
                            entity.hurt(ModDamageSource.soulflameIgnition(player), damage);
                            level.sendParticles(ParticleTypes.EXPLOSION, xx, yy, zz, 1, 0, 0, 0, 0);
                        }
                    }
                    k += 0.05;
                    if (Mth.nextInt(RandomSource.create(), 1, 4) <= 1 && k > 1) {
                        if (Mth.nextInt(RandomSource.create(), 1, 12) <= 1) {
                            dx = dx + Mth.nextDouble(RandomSource.create(), -0.7, 0.7);
                        } else if (Mth.nextInt(RandomSource.create(), 1, 2) <= 1) {
                                dy = dy + Mth.nextDouble(RandomSource.create(), -0.2, 0.2);
                        } else {
                                dz = dz + Mth.nextDouble(RandomSource.create(), -0.7, 0.7);
                        }
                    }
                }
            }
        }
    }

    public static void ashenForm(Player player){
        double x = player.getX();
        double y = player.getY() + 1.5;
        double z = player.getZ();
        Ember ember = Ember.ASHEN_FORM;
        float damage = ember.getDamage();
        double lx = player.getLookAngle().x;
        double ly = player.getLookAngle().y;
        double lz = player.getLookAngle().z;
        double k = 1.2f;
        player.setDeltaMovement(
                lx * k,
                ly * 0.1 * k,
                lz * k
        );
        for(LivingEntity entity : EntityUtils.entityCollector(new Vec3(x, y, z), 1, player.level)){
            if(!entity.is(player)){
                entity.hurt(ModDamageSource.ashenForm(player), damage);
            }
        }
    }

    public static void heavenlyFlame(Player player){
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        float damage = Ember.HEAVENLY_FLAME.getDamage();
        if(player.level instanceof ServerLevel level) {
            for (int i = 0; i < 3; i++) {
                level.sendParticles(ParticleTypes.FLAME, x, y + 2.25, z, (i+1)*10, (i+1)*0.5, 0.1, (i+1)*0.5, 0);
                for(LivingEntity entity : EntityUtils.entityCollector(new Vec3(x, y, z), 8, player.level)){
                    if(!entity.equals(player)){
                        FirePillarEntity heavenlyFlame = new FirePillarEntity(ModEntities.FIRE_PILLAR.get(), level);
                        heavenlyFlame.setSize(
                                entity.getBbWidth() / 0.6f
                        );
                        heavenlyFlame.setPlayerUuid(player.getUUID());
                        heavenlyFlame.setPos(
                                new Vec3(entity.getX(), entity.getY(), entity.getZ())
                        );
                        entity.hurt(ModDamageSource.heavenlyFlame(player), damage);
                        level.addFreshEntity(heavenlyFlame);
                    }
                }
            }
        }
    }

    public static void aegisOfFire(Player player){
        Ember ember = Ember.AEGIS_OF_FIRE;
        for(LivingEntity entity : EntityUtils.entityCollector(new Vec3(player.getX(), player.getY() + 1, player.getZ()), 4, player.level)){
            if(!entity.equals(player)){
                entity.hurt(ModDamageSource.aegisOfFire(player), ember.getDamage());
                entity.addEffect(new MobEffectInstance(ModMobEffects.MOLTEN_ARMOR.get(), 100,
                        entity.hasEffect(ModMobEffects.MOLTEN_ARMOR.get()) ? Objects.requireNonNull(entity.getEffect(ModMobEffects.MOLTEN_ARMOR.get())).getAmplifier() : 0));
            }
        }
    }

    public static void tornadoOfSouls(Player player) {
    }

    public static void empty(Player player){}

    // HERE GO EFFECTS

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void ashesOnHurtEffects(LivingHurtEvent event){
        if(event.getEntity() instanceof Player player){
            switch (player.getUseItem().getOrCreateTag().getString("ember")){
                case ("ashen_form") -> {
                    event.setCanceled(true);
                    player.hurtTime = 0;
                    player.hurtDuration = 0;
                    player.hurtMarked = false;
                    player.hurtDir = 0;
                }
                case("aegis_of_fire") ->{
                    aegisOfFire(player);
                    event.setAmount(event.getAmount() * 0.1f);
                }
            }
        }
    }
}
