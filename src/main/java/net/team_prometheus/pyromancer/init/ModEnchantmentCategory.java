package net.team_prometheus.pyromancer.init;

import net.minecraft.world.item.enchantment.EnchantmentCategory;
+import net.team_prometheus.pyromancer.items.BlazingJournal;
import net.team_prometheus.pyromancer.items.MaceItem;
import net.team_prometheus.pyromancer.items.PyromancyItem;

public class ModEnchantmentCategory {
    public static EnchantmentCategory MACES = EnchantmentCategory.create("MACES", MaceItem::isMace);
    public static EnchantmentCategory PYROMANCIES = EnchantmentCategory.create("PYROMANCIES", PyromancyItem::isPyromancy);
    public static EnchantmentCategory BLAZING_JOURNAL = EnchantmentCategory.create("JOURNAL", BlazingJournal::isJournal);
}
