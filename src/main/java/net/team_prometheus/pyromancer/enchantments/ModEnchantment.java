package net.team_prometheus.pyromancer.enchantments;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ModEnchantment extends Enchantment {
    protected ModEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(rarity, category, slots);
    }
    public double damageModifier(ItemStack itemStack, int level, LivingEntity attacker, Entity target){
        return 0;
    }
}
