package net.team_prometheus.pyromancer.mob_effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.team_prometheus.pyromancer.damage_source.ModDamageSource;
import org.jetbrains.annotations.NotNull;

public class IncinerationEffect extends MobEffect {
    protected IncinerationEffect() {
        super(MobEffectCategory.HARMFUL, 0);
    }
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int level) {
        entity.hurt(ModDamageSource.INCINERATION, (entity.getArmorValue() / 10F) * level + 1);
    }
}
