package net.team_prometheus.pyromancer.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.team_prometheus.pyromancer.items.blazing_journal.BlazingJournalItem;

import java.util.concurrent.atomic.AtomicReference;

public class ItemUtils {
    public static void changeBlaze(Player player, int amount){
        ItemStack supposedJournal = player.getMainHandItem();
        if(!(supposedJournal.getItem() instanceof BlazingJournalItem)) {
            supposedJournal = player.getOffhandItem();
        } if(!(supposedJournal.getItem() instanceof BlazingJournalItem)) return;
        supposedJournal.getOrCreateTag().putInt("blaze",
                supposedJournal.getOrCreateTag().getInt("blaze") + amount);
    }
    public static int getBlaze(Player player){
        ItemStack supposedJournal = player.getMainHandItem();
        if(!(supposedJournal.getItem() instanceof BlazingJournalItem)) supposedJournal = player.getOffhandItem();
        if(!(supposedJournal.getItem() instanceof BlazingJournalItem)) return 0;
        return supposedJournal.getOrCreateTag().getInt("blaze");
    }
    public static ItemStack getItemFromItem(ItemStack source, int id){
        AtomicReference<ItemStack> AR = new AtomicReference<>(ItemStack.EMPTY);
        source.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(capability -> AR.set(capability.getStackInSlot(id).copy()));
        return AR.get();
    }
    public static void setItemInItem(ItemStack source, ItemStack put, int id){
        source.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
            if(capability instanceof IItemHandlerModifiable handlerModifiable){
                handlerModifiable.setStackInSlot(id, put);
            }
        });
    }
}
