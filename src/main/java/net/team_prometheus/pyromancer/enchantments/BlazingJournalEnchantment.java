package net.team_prometheus.pyromancer.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class BlazingJournalEnchantment extends Enchantment {
    public final String targetType;
    protected BlazingJournalEnchantment(String type) {
        super(Enchantment.Rarity.COMMON, ModEnchantmentCategory.BLAZING_JOURNAL, new EquipmentSlot[]{});
        this.targetType = type;
    }
    public String getTargetType(){return targetType;}
}
