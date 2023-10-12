package net.team_prometheus.pyromancer.enchantments;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.extensions.IForgeEnchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.items.MaceItem;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class ModEnchantments implements IForgeEnchantment {
    public static DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, PyromancerMod.MOD_ID);
    public static RegistryObject<Enchantment> STURDINESS = ENCHANTMENTS.register("sturdiness",
            () -> new ModEnchantment(Enchantment.Rarity.COMMON, ModEnchantmentCategory.MACES, new EquipmentSlot[]{}) {
                @Override
                public int getMaxLevel(){return 5;}
                @Override
                public void doPostAttack(@NotNull LivingEntity attacker, @NotNull Entity target, int level){
                    if(target instanceof LivingEntity target_living){
                        target_living.hurt(DamageSource.GENERIC.bypassArmor(),
                                (float) (target_living.getArmorValue()*0.2));
                    }
                }
            });
    public static RegistryObject<Enchantment> COUNTERATTACK = ENCHANTMENTS.register("counterattack",
            () -> new ModEnchantment(Enchantment.Rarity.COMMON, ModEnchantmentCategory.MACES, new EquipmentSlot[]{}){
        @Override
        public int getMaxLevel() {return 5;}
        @Override
        public double damageModifier(ItemStack itemStack, int level, LivingEntity attacker, Entity target){
            if(attacker.hurtTime > 0){
                return 1.5 + (level+1)/5f;
            } return 1;
        }
    });
    public static RegistryObject<Enchantment> CLOSE_QUARTERS = ENCHANTMENTS.register("close_quarters",
            () -> new ModEnchantment(Enchantment.Rarity.COMMON, ModEnchantmentCategory.MACES, new EquipmentSlot[]{}){
        @Override
        public int getMaxLevel(){return 4;}
    });

    // ^^ maces

    public static RegistryObject<Enchantment> BLAZING_STRIKE = ENCHANTMENTS.register("blazing_strike", // axe
            () -> new BlazingJournalEnchantment(AxeItem.class, EnchantmentsHelper::axeAttack));
    public static RegistryObject<Enchantment> INCINERATING_BLOW = ENCHANTMENTS.register("incinerating_blow", // sword
            () -> new BlazingJournalEnchantment(SwordItem.class, EnchantmentsHelper::swordAttack));
    public static RegistryObject<Enchantment> COMBUSTION_LAUNCH = ENCHANTMENTS.register("combustion_launch", // shovel
            () -> new BlazingJournalEnchantment(ShovelItem.class, EnchantmentsHelper::shovelAttack));
    public static RegistryObject<Enchantment> MELTDOWN = ENCHANTMENTS.register("meltdown", // pickaxe
            () -> new BlazingJournalEnchantment(PickaxeItem.class, EnchantmentsHelper::pickaxeAttack));
    public static RegistryObject<Enchantment> INFERNAL_HARVEST = ENCHANTMENTS.register("infernal_harvest", // hoe
            () -> new BlazingJournalEnchantment(HoeItem.class, EnchantmentsHelper::hoeAttack));
    public static RegistryObject<Enchantment> ERUPTING_VALOR = ENCHANTMENTS.register("erupting_valor", // mace
            () -> new BlazingJournalEnchantment(MaceItem.class, EnchantmentsHelper::maceAttack));
}
