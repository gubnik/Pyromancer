package net.team_prometheus.pyromancer.enchantments;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.extensions.IForgeEnchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@SuppressWarnings("unused")
public class ModEnchantments implements IForgeEnchantment {
    public static DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, PyromancerMod.MOD_ID);
    public static RegistryObject<Enchantment> STURDINESS = ENCHANTMENTS.register("sturdiness",
            () -> new Enchantment(Enchantment.Rarity.COMMON, ModEnchantmentCategory.MACES, new EquipmentSlot[]{}) {
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
            () -> new Enchantment(Enchantment.Rarity.COMMON, ModEnchantmentCategory.MACES, new EquipmentSlot[]{}){
        @Override
        public int getMaxLevel() {return 5;}
        @Override
        public void doPostAttack(@NotNull LivingEntity attacker, @NotNull Entity target, int level){
            if(attacker.hurtTime > 0 && attacker instanceof Player player){
                target.hurt(DamageSource.playerAttack(player), level);
            }
        }
    });
    public static RegistryObject<Enchantment> CLOSE_QUARTERS = ENCHANTMENTS.register("close_quarters",
            () -> new Enchantment(Enchantment.Rarity.COMMON, ModEnchantmentCategory.MACES, new EquipmentSlot[]{}){
        @Override
        public int getMaxLevel(){return 4;}
    });

    // ^^ maces

    public static RegistryObject<Enchantment> BLAZING_STRIKE = ENCHANTMENTS.register("blazing_strike", // axe
            () -> new Enchantment(Enchantment.Rarity.RARE, ModEnchantmentCategory.BLAZING_JOURNAL, new EquipmentSlot[]{}) {
            });
    public static RegistryObject<Enchantment> INCINERATING_BLOW = ENCHANTMENTS.register("incinerating_blow", // sword
            () -> new Enchantment(Enchantment.Rarity.RARE, ModEnchantmentCategory.BLAZING_JOURNAL, new EquipmentSlot[]{}) {
            });
    public static RegistryObject<Enchantment> SIZZLING_BONK = ENCHANTMENTS.register("sizzling_bonk", // shovel
            // TODO: think of better name for this
            () -> new Enchantment(Enchantment.Rarity.RARE, ModEnchantmentCategory.BLAZING_JOURNAL, new EquipmentSlot[]{}) {
            });
    public static RegistryObject<Enchantment> MELTDOWN = ENCHANTMENTS.register("meltdown", // pickaxe
            () -> new Enchantment(Enchantment.Rarity.RARE, ModEnchantmentCategory.BLAZING_JOURNAL, new EquipmentSlot[]{}) {
            });
    public static RegistryObject<Enchantment> INFERNAL_HARVEST = ENCHANTMENTS.register("infernal_harvest", // hoe
            () -> new Enchantment(Enchantment.Rarity.RARE, ModEnchantmentCategory.BLAZING_JOURNAL, new EquipmentSlot[]{}) {
            });
    public static RegistryObject<Enchantment> ERUPTING_VALOR = ENCHANTMENTS.register("erupting_valor", // mace
            () -> new Enchantment(Enchantment.Rarity.RARE, ModEnchantmentCategory.BLAZING_JOURNAL, new EquipmentSlot[]{}) {
            });
}
