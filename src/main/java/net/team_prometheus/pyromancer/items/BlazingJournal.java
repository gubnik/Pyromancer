package net.team_prometheus.pyromancer.items;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.init.ModAttributes;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class BlazingJournal extends Item {
    private float blazeConsumption;
    private Multimap<Attribute, AttributeModifier> defaultModifiers;
    public BlazingJournal(Properties properties) {
        super(properties);

    }
    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int slot, boolean selected){
        this.blazeConsumption = switch ((int)itemStack.getOrCreateTag().getDouble("quill")){
            case(1), (2), (3) -> 1;
            default -> 0;
        };
        ImmutableListMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableListMultimap.builder();
        builder.putAll(super.getDefaultAttributeModifiers(EquipmentSlot.OFFHAND));
        builder.put(ModAttributes.BLAZE_CONSUMPTION.get(), new AttributeModifier(PyromancerMod.BLAZE_CONSUMPTION_UUID, "Journal modifier", this.blazeConsumption, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();

    }
    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        return slot == EquipmentSlot.OFFHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(slot);
    }

    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity living, ItemStack itemStack, int p_41431_) {
        itemStack.getOrCreateTag().putDouble("quill", 1);
    }
}
