package net.team_prometheus.pyromancer.entity.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.level.Level;

public class PyromancyFireballProjectile extends Fireball {
    public int lifetime;
    public int maxLifetime = 0;
    public int damage = 0;
    public PyromancyFireballProjectile(EntityType<? extends Fireball> p_37006_, Level p_37007_) {
        super(p_37006_, p_37007_);
    }
}
