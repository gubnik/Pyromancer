package net.team_prometheus.pyromancer.init;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.team_prometheus.pyromancer.entity.projectiles.SizzlingHandFireball;
import org.jetbrains.annotations.Nullable;

public class ModDamageSource {
    public static DamageSource INCINERATION = (new DamageSource("incineration")).setIsFire().bypassEnchantments();
    public static DamageSource ROT = (new DamageSource("rot")).bypassArmor().bypassEnchantments();
    public static DamageSource BOMBSACK = (new DamageSource("bombsack"));
    public static DamageSource sizzlingHandFireball(SizzlingHandFireball fireball, @Nullable Entity entity){
        return entity == null ? (new IndirectEntityDamageSource("onFire", fireball, fireball)).setIsFire().setProjectile() : (new IndirectEntityDamageSource("fireball", fireball, entity)).setIsFire().setProjectile();
    }
}
