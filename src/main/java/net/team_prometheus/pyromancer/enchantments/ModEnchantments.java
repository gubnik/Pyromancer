package net.team_prometheus.pyromancer.enchantments;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.extensions.IForgeEnchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;
import org.jetbrains.annotations.NotNull;
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
    // ^^ maces

    public static RegistryObject<Enchantment> BLASTING_STRIKE = ENCHANTMENTS.register("blazing_strike",
            () -> new BlazingJournalEnchantment("axe") {
        @Override
        public void actualAttack(@NotNull LivingEntity attacker, @NotNull Entity target, int level){
            if(target.isOnFire()){
               if(attacker instanceof ServerPlayer player){player.sendSystemMessage(Component.literal("aaa"));}
            }
        }
    });
}
