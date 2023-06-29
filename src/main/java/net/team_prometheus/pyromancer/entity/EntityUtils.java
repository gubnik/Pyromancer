package net.team_prometheus.pyromancer.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.team_prometheus.pyromancer.entity.projectiles.PyromancyFireballProjectile;
import net.team_prometheus.pyromancer.init.ModAttributes;
import net.team_prometheus.pyromancer.items.ItemUtils;

import javax.annotation.Nullable;

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
}
