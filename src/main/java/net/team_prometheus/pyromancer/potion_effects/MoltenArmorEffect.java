package net.team_prometheus.pyromancer.potion_effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.Objects;
import java.util.UUID;

public class MoltenArmorEffect extends MobEffect {
    public UUID MOLTEN_ARMOR_MODIFIER = UUID.fromString("13caa470-1df0-11ee-be56-0242ac120002");
    protected MoltenArmorEffect() {
        super(MobEffectCategory.HARMFUL, 0);
    }
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
    @Override
    public void applyEffectTick(LivingEntity entity, int level) {
        if(Objects.requireNonNull(entity.getEffect(this)).getDuration() > 2) {
            if (entity.getAttributeValue(Attributes.ARMOR) > 0
                    && !Objects.requireNonNull(entity.getAttribute(Attributes.ARMOR))
                    .hasModifier(new AttributeModifier(MOLTEN_ARMOR_MODIFIER, "Effect modifier", level * -1, AttributeModifier.Operation.ADDITION))) {
                Objects.requireNonNull(entity.getAttribute(Attributes.ARMOR)).addTransientModifier(
                        new AttributeModifier(MOLTEN_ARMOR_MODIFIER, "Effect modifier", level * -1, AttributeModifier.Operation.ADDITION)
                );
            }
        } else {
            Objects.requireNonNull(entity.getAttribute(Attributes.ARMOR)).removeModifier(MOLTEN_ARMOR_MODIFIER);
        }
    }
}
