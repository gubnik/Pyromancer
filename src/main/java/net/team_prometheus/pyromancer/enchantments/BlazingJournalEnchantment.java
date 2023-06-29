package net.team_prometheus.pyromancer.enchantments;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.team_prometheus.pyromancer.items.MaceItem;
import org.jetbrains.annotations.NotNull;

public class BlazingJournalEnchantment extends Enchantment {
    public final String targetType;
    protected BlazingJournalEnchantment(String type) {
        super(Enchantment.Rarity.COMMON, ModEnchantmentCategory.BLAZING_JOURNAL, new EquipmentSlot[]{});
        this.targetType = type;
    }
    @Override
    public void doPostAttack(@NotNull LivingEntity attacker, @NotNull Entity target, int level){
        Item attackingItem = attacker.getMainHandItem().getItem();
        boolean flag = switch (targetType){
            case("axe") -> (attackingItem instanceof AxeItem);
            case("sword") -> (attackingItem instanceof SwordItem);
            case("pickaxe") -> (attackingItem instanceof PickaxeItem);
            case("shovel") -> (attackingItem instanceof ShovelItem);
            case("hoe") -> (attackingItem instanceof HoeItem);
            case("mace") -> (attackingItem instanceof MaceItem);
            default -> false;
        };
        if(flag){
            actualAttack(attacker, target, level);
        }
    }
    public void actualAttack(@NotNull LivingEntity attacker, @NotNull Entity target, int level){}
    public String getTargetType(){return targetType;}
}
