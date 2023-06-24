package net.team_prometheus.pyromancer.items;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public enum ModTiers {
    HELLBLAZE(4, 2666, 10.0F, 5.0F, 16, () -> {
        return Ingredient.of(Items.NETHERITE_INGOT);
    });

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;
    private ModTiers(int level, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> ingredientSupplier) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = new LazyLoadedValue<>(ingredientSupplier);
    }
    public int getUses() {
        return this.uses;
    }
    public float getSpeed() {
        return this.speed;
    }
    public float getAttackDamageBonus() {
        return this.damage;
    }
    public int getLevel() {
        return this.level;
    }
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
