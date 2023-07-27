package net.team_prometheus.pyromancer.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.team_prometheus.pyromancer.entity.projectiles.PyromancyFireballProjectile;
import net.team_prometheus.pyromancer.init.ModAttributes;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;

public class EntityUtils {
    public static void shootPyromancyFireballProjectile(PyromancyFireballProjectile projectile, float speed, float inaccuracy, int timer, @Nullable Player shooter){
        assert shooter != null;
        projectile.setOwner(shooter);
        projectile.damage = (int) shooter.getAttributeValue(ModAttributes.PYROMANCY_DAMAGE.get());
        projectile.maxLifetime = timer;
        projectile.setPos(shooter.getX(), shooter.getEyeY() - 0.3, shooter.getZ());
        projectile.shoot(shooter.getLookAngle().x, shooter.getLookAngle().y, shooter.getLookAngle().z, speed, inaccuracy);
        shooter.level.addFreshEntity(projectile);
    }
    public static List<? extends LivingEntity> entityCollector(Vec3 center, double radius, Level level){
        return level.getEntitiesOfClass(LivingEntity.class, new AABB(center, center).inflate(radius), e -> true).stream().sorted(Comparator.comparingDouble(
                entityFound -> entityFound.distanceToSqr(center))).toList();
    }
}
