package net.team_prometheus.pyromancer.init;

import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;

public class ModEnchantments {
    public static DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, PyromancerMod.MOD_ID);
    public static RegistryObject<Enchantment> RUSH = ENCHANTMENTS.register("rush",
            ()-> new Enchantment(Enchantment.Rarity.COMMON, ModEnchantmentCategory.MACES, new EquipmentSlot[]{}){
        @Override
        public int getMaxLevel() {return 5;}
        @Override
        public void doPostAttack(LivingEntity attacker, Entity target, int level){
            float modifier = (float) Math.pow(
                    Math.pow(attacker.getDeltaMovement().get(Direction.Axis.X), 2) +
                    Math.pow(attacker.getDeltaMovement().get(Direction.Axis.Y), 2) +
                    Math.pow(attacker.getDeltaMovement().get(Direction.Axis.Z), 2)
            , 0.5);
            target.hurt(DamageSource.GENERIC.bypassInvul(), modifier);
        }
        @Override
        public boolean isAllowedOnBooks() {
            return true;
        }
    });
}
