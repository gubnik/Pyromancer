package net.team_prometheus.pyromancer.registries;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.team_prometheus.pyromancer.enchantments.ModEnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class ModTabs {
    public static final CreativeModeTab PYROMANCER_TAB = (new CreativeModeTab("pyromancer_tab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ModItems.BLAZING_JOURNAL.get());
        }
    }
    ).setEnchantmentCategories(ModEnchantmentCategory.MACES, ModEnchantmentCategory.BLAZING_JOURNAL);
}
