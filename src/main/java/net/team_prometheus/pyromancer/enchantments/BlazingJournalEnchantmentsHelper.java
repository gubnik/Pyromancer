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
@Mod.EventBusSubscriber//(modid = PyromancerMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlazingJournalEnchantmentsHelper {
    @SubscribeEvent
    public static void effects(AttackEntityEvent event){
        Player attacker = event.getEntity();
        Entity target = event.getTarget();
        if(event.getEntity().getOffhandItem().getItem() instanceof BlazingJournal){
            Map<Enchantment, Integer> enchantmentMap = event.getEntity().getOffhandItem().getAllEnchantments();
            Item attackingItem = attacker.getMainHandItem().getItem();
            for(Enchantment enchantment : enchantmentMap.keySet()){
                if(enchantment instanceof BlazingJournalEnchantment blazingJournalEnchantment) {
                    // TODO: clear out this mess, can't be bothered to do this now tho
                    if (attackingItem instanceof AxeItem && blazingJournalEnchantment.getTargetType().equals("axe")) {axeAttack(attacker, target);}
                    if (attackingItem instanceof SwordItem && blazingJournalEnchantment.getTargetType().equals("sword")) {swordAttack(attacker, target);}
                    if (attackingItem instanceof PickaxeItem && blazingJournalEnchantment.getTargetType().equals("pickaxe")) {pickaxeAttack(attacker, target);}
                    if (attackingItem instanceof ShovelItem && blazingJournalEnchantment.getTargetType().equals("shovel")) {shovelAttack(attacker, target);}
                    if (attackingItem instanceof HoeItem && blazingJournalEnchantment.getTargetType().equals("hoe")) {hoeAttack(attacker, target);}
                    if (attackingItem instanceof MaceItem && blazingJournalEnchantment.getTargetType().equals("mace")) {maceAttack(attacker, target);}
                }
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
