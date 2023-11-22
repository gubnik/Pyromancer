package net.team_prometheus.pyromancer.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.Enchantment;
import java.util.function.BiConsumer;

public class BlazingJournalEnchantment extends Enchantment {
    private final Class<? extends TieredItem> weaponClass;
    private final BiConsumer<Player, LivingEntity> attack;
    public BlazingJournalEnchantment(Class<? extends TieredItem> tieredClass, BiConsumer<Player, LivingEntity> biConsumer) {
        super(Rarity.UNCOMMON, ModEnchantmentCategory.BLAZING_JOURNAL, new EquipmentSlot[]{});
        this.weaponClass = tieredClass;
        this.attack = biConsumer;
    }
    public Class<? extends TieredItem> getWeaponClass(){
        return this.weaponClass;
    }
    public BiConsumer<Player, LivingEntity> getAttack(){
        return this.attack;
    }
}
