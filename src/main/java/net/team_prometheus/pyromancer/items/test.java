package net.team_prometheus.pyromancer.items;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class test {
    @SubscribeEvent
    public static void rightClick(PlayerInteractEvent.RightClickItem event){
        if(event != null && event.getItemStack().getItem() instanceof BlazingJournal){
            ItemStack item = event.getItemStack();
            item.getOrCreateTag().putDouble("quill", 1);
        }
    }
}
