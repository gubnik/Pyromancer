package net.team_prometheus.pyromancer.enchantments;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.team_prometheus.pyromancer.items.BlazingJournal;
import net.team_prometheus.pyromancer.items.MaceItem;

import java.util.Map;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber
public class BlazingJournalEnchantmentsHelper {
    @SubscribeEvent
    public static void effects(AttackEntityEvent event){
        Player attacker = event.getEntity();
        Entity target = event.getTarget();
        if(event.getEntity().getOffhandItem().getItem() instanceof BlazingJournal){
            Map<Enchantment, Integer> enchantmentMap = event.getEntity().getOffhandItem().getAllEnchantments();
            Item attackingItem = attacker.getMainHandItem().getItem();
            for(Enchantment enchantment : enchantmentMap.keySet()){
                if(enchantment.equals(ModEnchantments.BLASTING_STRIKE.get())){axeAttack(attacker, target);}
            }
        }
    }
    public static void axeAttack(Player attacker, Entity target){

    }
    public static void swordAttack(Player attacker, Entity target){

    }
    public static void shovelAttack(Player attacker, Entity target){

    }
    public static void pickaxeAttack(Player attacker, Entity target){

    }
    public static void hoeAttack(Player attacker, Entity target){

    }
    public static void maceAttack(Player attacker, Entity target){

    }
}
