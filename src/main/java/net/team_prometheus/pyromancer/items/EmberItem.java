package net.team_prometheus.pyromancer.items;

import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.team_prometheus.pyromancer.items.embers.Ember;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class EmberItem extends Item {
    public EmberItem(Properties properties) {
        super(properties);
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        if(!itemStack.getOrCreateTag().getString("ember").equals("")) {
            Ember ember = Ember.byName(itemStack.getOrCreateTag().getString("ember"));
            list.add(Component.translatable("desc.embers." + ember.getName()).withStyle(ember.getInfusionType().getColor()));
        }
    }
    @Override
    public void fillItemCategory(@NotNull CreativeModeTab creativeModeTab, @NotNull NonNullList<ItemStack> itemStacks) {
        if (this.allowedIn(creativeModeTab)) {
            itemStacks.add(new ItemStack(this));
            for(Ember ember : Ember.values()){
                ItemStack itemIter = new ItemStack(this);
                itemIter.getOrCreateTag().putString("ember", ember.getName());
                itemStacks.add(itemIter);
            }
        }
    }
}
