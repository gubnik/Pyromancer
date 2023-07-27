package net.team_prometheus.pyromancer.init;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.team_prometheus.pyromancer.entity.projectiles.SizzlingHandFireball;
import net.team_prometheus.pyromancer.entity.unburned.Unburned;
import org.jetbrains.annotations.Nullable;

public class ModDamageSource {
    public static DamageSource INCINERATION = (new DamageSource("incineration")).setIsFire().bypassEnchantments();
    public static DamageSource ROT = (new DamageSource("rot")).bypassArmor().bypassEnchantments();
    public static DamageSource BOMBSACK = (new DamageSource("bombsack"));
    public static DamageSource sizzlingHandFireball(SizzlingHandFireball fireball, @Nullable Entity entity){
        return new IndirectEntityDamageSource("onFire", fireball, entity).setIsFire().setProjectile();
    }
    public static DamageSource unburnedExplosion(Unburned unburned){
        return new EntityDamageSource("unburned_explosion", unburned).setIsFire().setExplosion();
    }
    public static DamageSource soulflameIgnition(Player player){
        return new EntityDamageSource("soulflame_ignition", player).setIsFire().setMagic();
    }
    public static DamageSource heavenlyFlame(Player player){
        return new EntityDamageSource("heavenly_flame", player).setIsFire().damageHelmet();
    }
    public static DamageSource aegisOfFire(Player player){
        return new EntityDamageSource("aegis_of_fire", player).setIsFire();
    }
}
