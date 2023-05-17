package net.team_prometheus.pyromancer.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.team_prometheus.pyromancer.items.ModItems;
import org.jetbrains.annotations.NotNull;

public class ModTabs {
    public static final CreativeModeTab PYROMANCER_TAB = new CreativeModeTab("pyromancer_tab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ModItems.BLAZING_JOURNAL.get());
        }
    };
}
