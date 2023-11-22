package net.team_prometheus.pyromancer.items.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.team_prometheus.pyromancer.entity.models.PyromancerArmorModel;
import net.team_prometheus.pyromancer.registries.ModAttributes;
import net.team_prometheus.pyromancer.registries.ModTabs;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Consumer;

public class PyromancerArmorItem extends ArmorItem {
    public final UUID FLAME_DAMAGE_UUID = UUID.fromString("50b74a18-4b50-11ee-be56-0242ac120002");
    public final UUID PYROMANCY_DAMAGE_UUID = UUID.fromString("6aada1d8-4b50-11ee-be56-0242ac120002");
    private final Multimap<Attribute, AttributeModifier> attributes;
    public PyromancerArmorItem(EquipmentSlot equipmentSlot) {
        super(ModArmorMaterials.PYROMANCER_ARMOR, equipmentSlot, new Properties().tab(ModTabs.PYROMANCER_TAB).stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON));
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(super.getDefaultAttributeModifiers(slot));
        builder.put(ModAttributes.FLAME_DAMAGE.get(), new AttributeModifier(FLAME_DAMAGE_UUID, "Flame damage modifier", this.getAttributeValue(equipmentSlot, ModAttributes.FLAME_DAMAGE.get()), AttributeModifier.Operation.MULTIPLY_BASE));
        builder.put(ModAttributes.PYROMANCY_DAMAGE.get(), new AttributeModifier(PYROMANCY_DAMAGE_UUID, "Pyromancy damage modifier", this.getAttributeValue(equipmentSlot, ModAttributes.PYROMANCY_DAMAGE.get()), AttributeModifier.Operation.MULTIPLY_BASE));
        this.attributes = builder.build();
    }
    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot equipmentSlot) {
        return equipmentSlot == this.slot ? this.attributes : super.getDefaultAttributeModifiers(equipmentSlot);
    }
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "pyromancer:textures/model/armor/pyromancer_armor.png";
    }
    public float getAttributeValue(EquipmentSlot equipmentSlot, Attribute attribute){
        int[] VALUES = new int[]{10, 20, 15, 10};
        return VALUES[equipmentSlot.getIndex()] * 0.01F * (attribute.equals(ModAttributes.PYROMANCY_DAMAGE.get()) ? 2 : 1);
    }
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            @SuppressWarnings("rawtypes")
            public @NotNull HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
                return PyromancerArmorModel.getHumanoidModel(slot);
            }
        });
    }
}
