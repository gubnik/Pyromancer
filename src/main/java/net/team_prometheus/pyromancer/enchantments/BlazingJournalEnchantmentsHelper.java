package net.team_prometheus.pyromancer.enchantments;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.team_prometheus.pyromancer.items.blazing_journal.BlazingJournal;
import net.team_prometheus.pyromancer.items.ItemUtils;
import net.team_prometheus.pyromancer.items.MaceItem;
import net.team_prometheus.pyromancer.potion_effects.ModEffects;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
@SuppressWarnings("unused")
@Mod.EventBusSubscriber
public class BlazingJournalEnchantmentsHelper {
    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void effects(AttackEntityEvent event){
        Player attacker = event.getEntity();
        Entity target = event.getTarget();
        if(attacker.getOffhandItem().getItem() instanceof BlazingJournal
        && attacker.getOffhandItem().getOrCreateTag().getInt("blaze") > 0
        && attacker.getAttackStrengthScale(0) > 0.7){
            Item attackingItem = attacker.getMainHandItem().getItem();
            for(Enchantment enchantment : attacker.getOffhandItem().getAllEnchantments().keySet()){
                if(enchantment.equals(ModEnchantments.BLAZING_STRIKE.get()) && attackingItem instanceof AxeItem){axeAttack(attacker, target);
                    ItemUtils.changeBlaze(attacker, -1);}
                if(enchantment.equals(ModEnchantments.INCINERATING_BLOW.get()) && attackingItem instanceof SwordItem){swordAttack(attacker, target);
                    ItemUtils.changeBlaze(attacker, -1);}
                if(enchantment.equals(ModEnchantments.SIZZLING_BONK.get()) && attackingItem instanceof ShovelItem){shovelAttack(attacker, target);
                    ItemUtils.changeBlaze(attacker, -1);}
                if(enchantment.equals(ModEnchantments.MELTDOWN.get()) && attackingItem instanceof PickaxeItem){pickaxeAttack(attacker, target);
                    ItemUtils.changeBlaze(attacker, -1);}
                if(enchantment.equals(ModEnchantments.INFERNAL_HARVEST.get()) && attackingItem instanceof HoeItem){hoeAttack(attacker, target);
                    ItemUtils.changeBlaze(attacker, -1);}
                if(enchantment.equals(ModEnchantments.ERUPTING_VALOR.get()) && attackingItem instanceof MaceItem){maceAttack(attacker, target);
                    ItemUtils.changeBlaze(attacker, -1);}
            }
        }
    }
    public static void axeAttack(Player attacker, Entity target){
        // Attacks create a special attack that deals incineration damage to all enemies hit; attack is vertical
        if(attacker.level instanceof ServerLevel level) {
            double vertical_degree = Math.toRadians(attacker.getXRot());
            double modifier = Objects.requireNonNull(attacker.getAttribute(ForgeMod.ATTACK_RANGE.get())).getValue() + 1;
            double x = attacker.getX();
            double y = attacker.getY();
            double z = attacker.getZ();
            for (int i = 180; i > 0; i--) {
                double new_x = x + attacker.getLookAngle().x * modifier * Math.sin(vertical_degree + Math.toRadians(i));
                double new_z = z + attacker.getLookAngle().z * modifier * Math.sin(vertical_degree + Math.toRadians(i));
                double new_y = y + Math.cos(vertical_degree + Math.toRadians(i)) * modifier;
                level.sendParticles(ParticleTypes.FLAME,
                        new_x,
                        new_y,
                        new_z,
                        2, 0.1, 0.1, 0.1, 0.05);
                Vec3 center = new Vec3(new_x, new_y, new_z);
                List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, new AABB(center, center).inflate(0.5D), entity -> true).stream().sorted(Comparator.comparingDouble(entity -> entity.distanceToSqr(center)))
                        .toList();
                for(LivingEntity entity : entities){
                    entity.hurt(DamageSource.playerAttack(attacker), (float) Objects.requireNonNull(attacker.getAttribute(Attributes.ATTACK_DAMAGE)).getValue());
                }
            }
        }
    }
    public static void swordAttack(Player attacker, Entity target){
        // Attacks create a special attack that deals incineration damage to all enemies hit; attack is horizontal
        if(attacker.level instanceof ServerLevel level) {
            double vertical_degree = Math.toRadians(attacker.getXRot());
            double horizontal_degree = Math.toRadians(attacker.getYRot());
            double modifier = Objects.requireNonNull(attacker.getAttribute(ForgeMod.ATTACK_RANGE.get())).getValue() + 1;
            double x = attacker.getX();
            double y = attacker.getY();
            double z = attacker.getZ();
            if(attacker instanceof ServerPlayer sp){sp.sendSystemMessage(Component.literal("хуй" + vertical_degree));}
            for (int i = -90; i < 90; i++) {
                double new_x = x - Math.sin(horizontal_degree + Math.toRadians(i)) * modifier;
                double new_y = y - Math.sin(vertical_degree) * Math.cos(Math.toRadians(i)) * modifier;
                double new_z = z + Math.cos(horizontal_degree + Math.toRadians(i)) * modifier;
                level.sendParticles(ParticleTypes.FLAME,
                        new_x,
                        new_y + 1.4,
                        new_z,
                        2, 0.2, 0.1, 0.2, 0.05);
                Vec3 center = new Vec3(new_x, new_y + 1.4, new_z);
                List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, new AABB(center, center).inflate(0.5D), entity -> true).stream()
                        .sorted(Comparator.comparingDouble(entity -> entity.distanceToSqr(center)))
                        .toList();
                for(LivingEntity entity : entities){
                    entity.hurt(DamageSource.playerAttack(attacker), (float) Objects.requireNonNull(attacker.getAttribute(Attributes.ATTACK_DAMAGE)).getValue());
                }
            }
        }
    }
    public static void shovelAttack(Player attacker, Entity target){
        // Attacks launch enemies
        double modifier = ( attacker.getAttributeValue(Attributes.ATTACK_DAMAGE) + attacker.getAttributeValue(Attributes.ATTACK_KNOCKBACK)
        ) / attacker.distanceTo(target) + attacker.getAttributeValue(ForgeMod.ATTACK_RANGE.get());
        target.setDeltaMovement(
                new Vec3(
                        attacker.getLookAngle().x * modifier,
                        -Math.pow(attacker.getLookAngle().y * modifier + modifier, 2),
                        attacker.getLookAngle().z * modifier
                )
        );
    }
    public static void pickaxeAttack(Player attacker, Entity target){
        if(target instanceof LivingEntity livTarget) {
            if (livTarget.hasEffect(ModEffects.MOLTEN_ARMOR.get())) {
                livTarget.addEffect(new MobEffectInstance(ModEffects.MOLTEN_ARMOR.get(), 100,
                        Objects.requireNonNull(
                                livTarget.getEffect(ModEffects.MOLTEN_ARMOR.get())).getAmplifier())
                );
            } else {
                livTarget.addEffect((new MobEffectInstance(ModEffects.MOLTEN_ARMOR.get(), 100, 0)));
            }
        }
    }
    public static void hoeAttack(Player attacker, Entity target){
        // Attacks pull nearby enemies closer
    }
    public static void maceAttack(Player attacker, Entity target){
        // Attacks performed after being hurt create an explosion of fire at your position
        // TODO: figure this shit out, it sounds cool as fuck but also troublesome as fuck
    }
    // TODO: implement those
    // TODO: to future me, don't forget to make these thingies ignite enemies
}
