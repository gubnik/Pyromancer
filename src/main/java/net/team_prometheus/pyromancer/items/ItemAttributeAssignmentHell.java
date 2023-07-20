package net.team_prometheus.pyromancer.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.enchantments.ModEnchantments;
import net.team_prometheus.pyromancer.init.ModAttributes;

import java.util.UUID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber
public class ItemAttributeAssignmentHell {
    protected static final UUID BASE_ATTACK_DAMAGE_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    protected static final UUID BASE_ATTACK_SPEED_UUID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
    @SubscribeEvent
    public static void itemEvent(ItemAttributeModifierEvent event){
        ItemStack itemStack = event.getItemStack();
        Item item = itemStack.getItem();
        switch (item.toString()) {
            case ("blazing_journal") -> {
                if(event.getSlotType() == EquipmentSlot.OFFHAND) {
                    int quill = itemStack.getOrCreateTag().getInt("quill");
                    int consumption = switch (quill) {
                        case (1) -> 2;
                        case (0) -> 1;
                        default -> 0;
                    };
                    event.addModifier(ModAttributes.BLAZE_CONSUMPTION.get(),
                            new AttributeModifier(PyromancerMod.JOURNAL_BLAZE_CONSUMPTION_UUID, "Journal modifier",
                                    consumption, AttributeModifier.Operation.ADDITION));
                    if (quill == 1) {
                        event.addModifier(ModAttributes.PYROMANCY_DAMAGE.get(),
                                new AttributeModifier(PyromancerMod.JOURNAL_PYROMANCY_DAMAGE_UUID, "Weapon modifier",
                                        1, AttributeModifier.Operation.ADDITION));
                    }
                }
            }
            case ("sizzling_hand") -> {
                if (event.getSlotType() == EquipmentSlot.MAINHAND) {
                    event.addModifier(ModAttributes.BLAZE_CONSUMPTION.get(),
                            new AttributeModifier(PyromancerMod.BASE_BLAZE_CONSUMPTION_UUID, "Journal modifier",
                                    1, AttributeModifier.Operation.ADDITION));
                    event.addModifier(ModAttributes.PYROMANCY_DAMAGE.get(),
                            new AttributeModifier(PyromancerMod.BASE_PYROMANCY_DAMAGE_UUID, "Weapon modifier",
                                    4, AttributeModifier.Operation.ADDITION));
                }
            }
        }
        if(item instanceof MaceItem mace){
            if(event.getSlotType() == EquipmentSlot.MAINHAND) {
                if (itemStack.getAllEnchantments().containsKey(ModEnchantments.CLOSE_QUARTERS.get())) {
                    // range
                    event.addModifier(ForgeMod.ATTACK_RANGE.get(),
                            new AttributeModifier(PyromancerMod.MACE_CC_RANGE_MODIFIER_UUID, "Weapon modifier",
                                    (itemStack.getEnchantmentLevel(ModEnchantments.CLOSE_QUARTERS.get()) + 1) * -0.25, AttributeModifier.Operation.ADDITION));
                    // toughness
                    event.addModifier(Attributes.ARMOR_TOUGHNESS,
                            new AttributeModifier(PyromancerMod.MACE_CC_TOUGHNESS_UUID, "Weapon modifier",
                                    (itemStack.getEnchantmentLevel(ModEnchantments.CLOSE_QUARTERS.get()) + 1) * 0.2, AttributeModifier.Operation.ADDITION));
                }
            }
        }
    }
}
