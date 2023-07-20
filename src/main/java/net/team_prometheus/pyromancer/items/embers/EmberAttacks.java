package net.team_prometheus.pyromancer.items.embers;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.team_prometheus.pyromancer.entity.EntityUtils;
import net.team_prometheus.pyromancer.init.ModDamageSource;
@SuppressWarnings("unused")
public class EmberAttacks {
    public static int soulflameIgnition(Player player){
        Ember ember = Ember.SOULFLAME_IGNITION;
        float damage = (float) ember.getDamage();
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
                    for (LivingEntity entity : EntityUtils.entityCollector(
                            new Vec3(xx, yy, zz), 2, level)){
                        if (!entity.equals(player)) {
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
        return 0;
    }
    public static int ashenForm(Player player){
        double x = player.getX();
        double y = player.getY() + 1.5;
        double z = player.getZ();
        int tick = player.tickCount;
        Ember ember = Ember.ASHEN_FORM;
        float damage = (float) ember.getDamage();
        double lx = player.getLookAngle().x;
        double ly = player.getLookAngle().y;
        double lz = player.getLookAngle().z;
        double k = 1.5f;
        player.setDeltaMovement(
                lx * k,
                ly * k,
                lz * k
        );
        return 0;
    }
    public static int heavenlyFlame(Player player){
        double x = player.getX();
        double y = player.getY() + 1.5;
        double z = player.getZ();
        return 0;
    }
    public static void heavenlyFlameSupport(int delay, double x, double y, double z, Player player){
        //
    }
}
