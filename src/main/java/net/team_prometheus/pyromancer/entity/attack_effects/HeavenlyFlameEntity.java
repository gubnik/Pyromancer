package net.team_prometheus.pyromancer.entity.attack_effects;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class HeavenlyFlameEntity extends AttackEffectEntity{

    public HeavenlyFlameEntity(EntityType<HeavenlyFlameEntity> entityType, Level level) {
        super(entityType, level);
    }
    @Override
    public void tick(){
        if(this.tickCount > 8) this.remove(RemovalReason.DISCARDED);
    }
}
