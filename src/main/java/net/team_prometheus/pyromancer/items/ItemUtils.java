package net.team_prometheus.pyromancer.items;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.team_prometheus.pyromancer.items.blazing_journal.BlazingJournal;
import net.team_prometheus.pyromancer.items.weaponary.pyromancy.PyromancyItem;

import java.util.concurrent.atomic.AtomicReference;

public class ItemUtils {
    public static void changeBlaze(Player player, int amount){
        ItemStack supposedJournal = player.getOffhandItem();
        if(supposedJournal.getItem() instanceof BlazingJournal){
            supposedJournal.getOrCreateTag().putInt("blaze",
                    supposedJournal.getOrCreateTag().getInt("blaze") + amount);
        }
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
    public static boolean isPyromancy(Item item){
        return(item instanceof PyromancyItem);
    }
}
