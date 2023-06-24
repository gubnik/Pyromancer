package net.team_prometheus.pyromancer.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.team_prometheus.pyromancer.init.ModAttributes;

public class ItemUtils {
    public static  int getBlazeConsumption(Player player){
        return (int) player.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND).get(ModAttributes.BLAZE_CONSUMPTION.get()).stream().mapToDouble(AttributeModifier::getAmount).sum();
    }
}
