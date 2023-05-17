package net.team_prometheus.pyromancer.items;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.team_prometheus.pyromancer.PyromancerMod;
import org.jetbrains.annotations.NotNull;

public class MaceItem extends TieredItem implements Vanishable {
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;
    public MaceItem(Tier tier, float damage, float attackSpeed, float toughness, Item.Properties properties) {
        super(tier, properties);
        this.attackDamage = damage + tier.getAttackDamageBonus();
        ImmutableListMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableListMultimap.builder();
        builder.putAll(super.getDefaultAttributeModifiers(EquipmentSlot.MAINHAND));
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(PyromancerMod.BASE_ARMOR_TOUGHNESS_UUID, "Weapon modifier", toughness, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }
    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(slot);
    }
    @Override
    public boolean hurtEnemy(ItemStack itemStack, @NotNull LivingEntity livingEntity, @NotNull LivingEntity entity) {
        itemStack.hurtAndBreak(1, entity, (entity1) -> {
            entity1.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }
    public float getDamage() {
        return this.attackDamage;
    }
    public static boolean isMace(Item item){
        return(item instanceof MaceItem);
    }
}
