package net.team_prometheus.pyromancer.entity.attack_effects;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class FirePillarEntity extends AttackEffectEntity{
    public FirePillarEntity(EntityType<FirePillarEntity> entityType, Level level) {
        super(entityType, level);
        this.lifetime = 10;
    }
}
