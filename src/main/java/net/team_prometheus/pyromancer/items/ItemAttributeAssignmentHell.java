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
import net.team_prometheus.pyromancer.registries.ModEnchantments;
import net.team_prometheus.pyromancer.registries.ModAttributes;
import net.team_prometheus.pyromancer.items.blazing_journal.QuillItem;
import net.team_prometheus.pyromancer.items.compendium.CompendiumOfFlame;
import net.team_prometheus.pyromancer.items.weaponary.MaceItem;
import net.team_prometheus.pyromancer.items.weaponary.pyromancy.PyromancyItem;
import net.team_prometheus.pyromancer.registries.ModItems;
import net.team_prometheus.pyromancer.util.ItemUtils;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber
public class ItemAttributeAssignmentHell {
    @SubscribeEvent
    public static void itemEvent(ItemAttributeModifierEvent event){
        ItemStack itemStack = event.getItemStack();
        Item item = itemStack.getItem();

        if(item.equals(ModItems.BLAZING_JOURNAL.get())){
            if(event.getSlotType() == EquipmentSlot.OFFHAND){
                String quill = itemStack.getOrCreateTag().getString("quill");
                int costModifier = 0;
                double damageModifier = 0;
                if(QuillItem.QuillEnum.getQuillItem(quill) instanceof QuillItem quillItem){
                    costModifier = quillItem.blazeConsumptionModifier;
                    damageModifier = quillItem.pyromancyDamageModifier;
                }
                event.addModifier(ModAttributes.BLAZE_CONSUMPTION.get(),
                        new AttributeModifier(PyromancerMod.JOURNAL_BLAZE_CONSUMPTION_UUID, "Journal modifier",
                                costModifier, AttributeModifier.Operation.ADDITION));
                event.addModifier(ModAttributes.PYROMANCY_DAMAGE.get(),
                        new AttributeModifier(PyromancerMod.JOURNAL_PYROMANCY_DAMAGE_UUID, "Journal modifier",
                                damageModifier, AttributeModifier.Operation.ADDITION));
            }
        }

        if(item instanceof PyromancyItem pyromancyItem && event.getSlotType() == EquipmentSlot.MAINHAND){
            event.addModifier(ModAttributes.BLAZE_CONSUMPTION.get(),
                    new AttributeModifier(PyromancerMod.BASE_BLAZE_CONSUMPTION_UUID, "Weapon modifier",
                            pyromancyItem.blazeConsumptionModifier, AttributeModifier.Operation.ADDITION));
            event.addModifier(ModAttributes.PYROMANCY_DAMAGE.get(),
                    new AttributeModifier(PyromancerMod.BASE_PYROMANCY_DAMAGE_UUID, "Weapon modifier",
                            pyromancyItem.pyromancyDamageModifier, AttributeModifier.Operation.ADDITION));
        }
        if(item instanceof CompendiumOfFlame && event.getSlotType() == EquipmentSlot.MAINHAND){
            ItemStack activeItem = ItemUtils.getItemFromItem(itemStack, itemStack.getOrCreateTag().getInt("active_slot"));
            if(activeItem != ItemStack.EMPTY){
                PyromancyItem pyromancyItem = (PyromancyItem) activeItem.getItem();
                event.addModifier(ModAttributes.BLAZE_CONSUMPTION.get(),
                        new AttributeModifier(PyromancerMod.BASE_BLAZE_CONSUMPTION_UUID, "Weapon modifier",
                                pyromancyItem.blazeConsumptionModifier, AttributeModifier.Operation.ADDITION));
                event.addModifier(ModAttributes.PYROMANCY_DAMAGE.get(),
                        new AttributeModifier(PyromancerMod.BASE_PYROMANCY_DAMAGE_UUID, "Weapon modifier",
                                pyromancyItem.pyromancyDamageModifier, AttributeModifier.Operation.ADDITION));
            }
        }

        if(item instanceof MaceItem mace && event.getSlotType() == EquipmentSlot.MAINHAND){
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
