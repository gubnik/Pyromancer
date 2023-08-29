package net.team_prometheus.pyromancer.damage_source;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.team_prometheus.pyromancer.entity.projectiles.SizzlingHandFireball;
import net.team_prometheus.pyromancer.entity.unburned.Unburned;
import org.jetbrains.annotations.Nullable;

public class ModDamageSource {
    public static DamageSource INCINERATION = (new PyromancyDamageSource("incineration")).setHellblaze().bypassEnchantments();
    public static DamageSource ROT = (new DamageSource("rot")).bypassArmor().bypassEnchantments();
    public static DamageSource BOMBSACK = (new DamageSource("bombsack"));
    public static DamageSource sizzlingHandFireball(SizzlingHandFireball fireball, @Nullable Entity entity){
        return new IndirectEntityDamageSource("onFire", fireball, entity).setIsFire().setProjectile();
    }
    public static DamageSource unburnedExplosion(Unburned unburned){
        return new EntityDamageSource("unburned_explosion", unburned).setIsFire().setExplosion();
    }
    public static DamageSource soulflameIgnition(Player player){
        return new EntityPyromancyDamageSource("soulflame_ignition", player).setIsEmber().setSoulflame();
    }
    public static DamageSource heavenlyFlame(Player player){
        return new EntityPyromancyDamageSource("heavenly_flame", player).setIsEmber().setHellblaze().damageHelmet();
    }
    public static DamageSource aegisOfFire(Player player){
        return new EntityPyromancyDamageSource("aegis_of_fire", player).setIsEmber().setFlame();
    }
    public static DamageSource ashenForm(Player player){
        return new EntityPyromancyDamageSource("ashen_form", player).setIsEmber().setFlame().bypassArmor();
    }
}
