package net.team_prometheus.pyromancer.items.embers;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.team_prometheus.pyromancer.items.MaceItem;

@Mod.EventBusSubscriber
public class EmbersTooltipProvider {
    @SubscribeEvent
    public static void event(ItemTooltipEvent event){
        ItemStack itemStack = event.getItemStack();
        if((itemStack.getItem() instanceof SwordItem
        || itemStack.getItem() instanceof MaceItem || itemStack.getItem() instanceof AxeItem)
        && Ember.isValidEmber(itemStack.getOrCreateTag().getString("ember"))) {
            if (Screen.hasAltDown()) {
                String emberTag = itemStack.getOrCreateTag().getString("ember");
                event.getToolTip().add(
                        Component.translatable("desc.embers.main").withStyle(ChatFormatting.GOLD)
                );
                event.getToolTip().add(
                        Component.translatable("desc.embers." + emberTag).withStyle(
                                Ember.byName(emberTag).getInfusionType().getColor()
                        )
                );
                event.getToolTip().add(
                        Component.translatable("desc.embers." + emberTag + ".effect").withStyle(
                                Ember.byName(emberTag).getInfusionType().getColor()
                        )
                );
            } else event.getToolTip().add(Component.translatable("desc.embers.press_alt").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}

