package net.team_prometheus.pyromancer.damage_source;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;

public class ModDamageSource {
    public static DamageSource INCINERATION = (new PyromancyDamageSource("incineration")).setHellblaze().bypassEnchantments();
    public static DamageSource ROT = (new DamageSource("rot")).bypassArmor().bypassEnchantments();
    public static DamageSource BOMBSACK = (new DamageSource("bombsack"));
    public static DamageSource cryomancy(Entity entity){
        return new EntityDamageSource("cryomancy", entity);
    }
    public static DamageSource unburnedExplosion(Entity unburned){
        return new EntityDamageSource("unburned_explosion", unburned).setIsFire().setExplosion();
    }
    public static DamageSource soulflameIgnition(Entity player){
        return new EntityPyromancyDamageSource("soulflame_ignition", player).setIsEmber().setSoulflame();
    }
    public static DamageSource heavenlyFlame(Entity player){
        return new EntityPyromancyDamageSource("heavenly_flame", player).setIsEmber().setHellblaze().damageHelmet();
    }
    public static DamageSource aegisOfFire(Entity player){
        return new EntityPyromancyDamageSource("aegis_of_fire", player).setIsEmber().setFlame();
    }
    public static DamageSource ashenForm(Entity player){
        return new EntityPyromancyDamageSource("ashen_form", player).setIsEmber().setFlame().bypassArmor();
    }
    public static DamageSource sizzlingHand(Entity player){
        return new EntityPyromancyDamageSource("sizzling_hand", player).setFlame().setProjectile();
    }
    public static DamageSource spark(Entity player){
        return new EntityPyromancyDamageSource("spark", player).setHellblaze().setProjectile();
    }
}
