package net.team_prometheus.pyromancer.items;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.*;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.init.ModAttributes;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class PyromancyItem extends Item implements Vanishable {
    private final int blazeConsumption;
    private final int damage;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;
    public PyromancyItem(int damage, int blazeCost, int uses, Properties properties) {
        super(properties.stacksTo(1).defaultDurability(uses));
        this.blazeConsumption = blazeCost;
        this.damage = damage;
        ImmutableListMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableListMultimap.builder();
        builder.putAll(super.getDefaultAttributeModifiers(EquipmentSlot.MAINHAND));
        builder.put(ModAttributes.PYROMANCY_DAMAGE.get(), new AttributeModifier(PyromancerMod.PYROMANCY_DAMAGE_UUID, "Weapon modifier", this.damage, AttributeModifier.Operation.ADDITION));
        builder.put(ModAttributes.BLAZE_CONSUMPTION.get(), new AttributeModifier(PyromancerMod.BLAZE_CONSUMPTION_UUID, "Weapon modifier", this.blazeConsumption, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }
    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(slot);
    }
    public int getCost(){
        return this.blazeConsumption;
    }
    public static boolean isPyromancy(Item item){
        return(item instanceof PyromancyItem);
    }
}
