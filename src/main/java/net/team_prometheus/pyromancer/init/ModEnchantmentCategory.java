package net.team_prometheus.pyromancer.init;

import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.team_prometheus.pyromancer.items.MaceItem;

public class ModEnchantmentCategory {
    public static EnchantmentCategory MACES = EnchantmentCategory.create("MACES", MaceItem::isMace);
}
