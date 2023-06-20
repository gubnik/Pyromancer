package net.team_prometheus.pyromancer.init;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;
import org.jetbrains.annotations.NotNull;

public class ModEnchantments {
    public static DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, PyromancerMod.MOD_ID);
    public static RegistryObject<Enchantment> RUSH = ENCHANTMENTS.register("rush",
            ()-> new Enchantment(Enchantment.Rarity.COMMON, ModEnchantmentCategory.MACES, new EquipmentSlot[]{}){
        @Override
        public int getMaxLevel() {return 5;}
        @Override
        public void doPostAttack(@NotNull LivingEntity attacker, @NotNull Entity target, int level){
            double modifier = Math.sqrt(
                    Math.pow(attacker.getDeltaMovement().x(), 2) +
                    Math.pow(attacker.getDeltaMovement().z(), 2)
            );
            if(attacker instanceof ServerPlayer serverPlayer){
                serverPlayer.sendSystemMessage(Component.literal("x: " + String.valueOf(attacker.getDeltaMovement().x())));
                serverPlayer.sendSystemMessage(Component.literal("z: " + String.valueOf(attacker.getDeltaMovement().z())));
                serverPlayer.sendSystemMessage(Component.literal("speed: " + String.valueOf(attacker.getSpeed())));
            }
            target.hurt(DamageSource.GENERIC.bypassInvul(), (float) modifier);
        }
        @Override
        public boolean isAllowedOnBooks() {
            return true;
        }
    });
    public static RegistryObject<Enchantment> STURDINESS = ENCHANTMENTS.register("sturdiness",
            () -> new Enchantment(Enchantment.Rarity.COMMON, ModEnchantmentCategory.MACES, new EquipmentSlot[]{}) {
        @Override
        public int getMaxLevel(){return 5;
        }
        @Override
        public void doPostAttack(@NotNull LivingEntity attacker, @NotNull Entity target, int level){
            if(target instanceof LivingEntity target_living){
                target_living.hurt(DamageSource.GENERIC.bypassInvul().bypassArmor(),
                        (float) (target_living.getArmorValue()*0.2));
            }
        }
    });
}
