package net.team_prometheus.pyromancer.init;

import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.team_prometheus.pyromancer.items.BlazingJournal;
import net.team_prometheus.pyromancer.items.ItemUtils;
import net.team_prometheus.pyromancer.items.MaceItem;

public class ModEnchantmentCategory {
    public static EnchantmentCategory MACES = EnchantmentCategory.create("MACES", MaceItem::isMace);
    public static EnchantmentCategory PYROMANCIES = EnchantmentCategory.create("PYROMANCIES", ItemUtils::isPyromancy);
    public static EnchantmentCategory BLAZING_JOURNAL = EnchantmentCategory.create("JOURNAL", BlazingJournal::isJournal);
}
