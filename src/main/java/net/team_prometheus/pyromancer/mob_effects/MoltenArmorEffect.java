package net.team_prometheus.pyromancer.mob_effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class MoltenArmorEffect extends MobEffect {
    public UUID MOLTEN_ARMOR_MODIFIER = UUID.fromString("13caa470-1df0-11ee-be56-0242ac120002");
    protected MoltenArmorEffect() {
        super(MobEffectCategory.HARMFUL, -7114906);
        this.addAttributeModifier(Attributes.ARMOR, "Effect modifier", -1, AttributeModifier.Operation.ADDITION);
    }
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
    @Override
    public @NotNull MobEffect addAttributeModifier(@NotNull Attribute attribute, @NotNull String desc, double amount, AttributeModifier.@NotNull Operation operation) {
        AttributeModifier attributemodifier = new AttributeModifier(this.MOLTEN_ARMOR_MODIFIER, desc, amount, operation);
        this.attributeModifiers.put(attribute, attributemodifier);
        return this;
    }
    //@Override
    //public void applyEffectTick(LivingEntity entity, int level) {
    //    if(Objects.requireNonNull(entity.getEffect(this)).getDuration() > 2) {
    //        if (entity.getAttributeValue(Attributes.ARMOR) > 0
    //                && !Objects.requireNonNull(entity.getAttribute(Attributes.ARMOR))
    //                .hasModifier(new AttributeModifier(MOLTEN_ARMOR_MODIFIER, "Effect modifier", level * -1, AttributeModifier.Operation.ADDITION))) {
    //            Objects.requireNonNull(entity.getAttribute(Attributes.ARMOR)).addTransientModifier(
    //                    new AttributeModifier(MOLTEN_ARMOR_MODIFIER, "Effect modifier", level * -1, AttributeModifier.Operation.ADDITION)
    //            );
    //        }
    //    } else {
    //        Objects.requireNonNull(entity.getAttribute(Attributes.ARMOR)).removeModifier(MOLTEN_ARMOR_MODIFIER);
    //    }
    //}
}
