package net.team_prometheus.pyromancer.blaze;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.items.ModItems;
import net.team_prometheus.pyromancer.items.PyromancyItem;

@Mod.EventBusSubscriber(modid = PyromancerMod.MOD_ID)
public class BlazeRegister {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent event){
        if(event.getObject() instanceof Player){
            if(!((Player) event.getObject()).getCapability(PlayerBlazeProvider.PLAYER_BLAZE).isPresent()){
                event.addCapability(new ResourceLocation(PyromancerMod.MOD_ID, "properties"), new PlayerBlazeProvider());
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event){
        if(event.isWasDeath()){
            event.getOriginal().getCapability(PlayerBlazeProvider.PLAYER_BLAZE).ifPresent(oldStore ->
                    event.getOriginal().getCapability(PlayerBlazeProvider.PLAYER_BLAZE).ifPresent(newStore ->
                            newStore.copyFrom(oldStore)));
        }
    }
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){
        event.register(PlayerBlaze.class);
    }
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        if(event.player.getOffhandItem().getItem() == ModItems.BLAZING_JOURNAL.get()){
            ItemStack journal = event.player.getOffhandItem();
            int blaze_journal = journal.getOrCreateTag().getInt("blaze");
            event.player.getCapability(PlayerBlazeProvider.PLAYER_BLAZE).ifPresent(playerBlaze ->{
                playerBlaze.setBlaze(blaze_journal);
            });
        }
    }
}
