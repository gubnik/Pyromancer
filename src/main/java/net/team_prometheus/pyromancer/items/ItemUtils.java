package net.team_prometheus.pyromancer.items;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemUtils {
    public static void changeBlaze(Player player, int amount){
        ItemStack supposedJournal = player.getOffhandItem();
        if(supposedJournal.getItem() instanceof BlazingJournal){
            supposedJournal.getOrCreateTag().putInt("blaze",
                    supposedJournal.getOrCreateTag().getInt("blaze") + amount);
        }
    }
    public static boolean isPyromancy(Item item){
        return(item instanceof IPyromancy);
    }
}
