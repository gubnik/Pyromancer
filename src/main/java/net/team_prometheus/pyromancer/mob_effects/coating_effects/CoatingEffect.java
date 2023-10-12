package net.team_prometheus.pyromancer.mob_effects.coating_effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collection;

public class CoatingEffect extends MobEffect {
    protected CoatingEffect(MobEffectCategory mobEffectCategory, int i) {
        super(mobEffectCategory, i);
    }
    public void applyEffectTick(LivingEntity livingEntity, int level){
        Collection<MobEffectInstance> mobEffectInstances = livingEntity.getActiveEffects();
        mobEffectInstances.removeIf(mobEffectInstance -> mobEffectInstance.getEffect() != this);
    }
}
