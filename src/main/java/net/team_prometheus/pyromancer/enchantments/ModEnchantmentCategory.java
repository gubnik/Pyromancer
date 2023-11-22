package net.team_prometheus.pyromancer.enchantments;

import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.team_prometheus.pyromancer.items.blazing_journal.BlazingJournalItem;
import net.team_prometheus.pyromancer.items.weaponary.MaceItem;

public class ModEnchantmentCategory {
    public static EnchantmentCategory MACES = EnchantmentCategory.create("MACES", MaceItem::isMace);
    public static EnchantmentCategory BLAZING_JOURNAL = EnchantmentCategory.create("JOURNAL", BlazingJournalItem::isJournal);
}
