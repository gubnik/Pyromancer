package net.team_prometheus.pyromancer.items;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.team_prometheus.pyromancer.init.ModAttributes;

import java.util.Collection;

public class ItemUtils {
    public static int getBlazeConsumption(Player player){
        double blazeConsumption = 0;
        for(EquipmentSlot equipmentSlot : EquipmentSlot.values()){
            ItemStack itemStack = player.getItemBySlot(equipmentSlot);
            Collection<AttributeModifier> modifiers = itemStack.getAttributeModifiers(equipmentSlot)
                    .get(ModAttributes.BLAZE_CONSUMPTION.get());
            if(modifiers.isEmpty()) continue;
            blazeConsumption += modifiers.stream().mapToDouble(AttributeModifier::getAmount).sum();
        } return (int) blazeConsumption;
    }
    public static int getPyromancyDamage(Player player){
        double pyromancyDamage = 0;
        for(EquipmentSlot equipmentSlot : EquipmentSlot.values()){
            ItemStack itemStack = player.getItemBySlot(equipmentSlot);
            Collection<AttributeModifier> modifiers = itemStack.getAttributeModifiers(equipmentSlot)
                    .get(ModAttributes.PYROMANCY_DAMAGE.get());
            if(modifiers.isEmpty()) continue;
            pyromancyDamage += modifiers.stream().mapToDouble(AttributeModifier::getAmount).sum();
        } return (int) pyromancyDamage;
    }
    public static void changeBlaze(Player player, int amount){
        ItemStack supposedJournal = player.getOffhandItem();
        if(supposedJournal.getItem() instanceof BlazingJournal){
            supposedJournal.getOrCreateTag().putInt("blaze",
                    supposedJournal.getOrCreateTag().getInt("blaze") + amount);
        }
    }
}
