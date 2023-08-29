package net.team_prometheus.pyromancer.mob_effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;

public class ModMobEffects {
    public static DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, PyromancerMod.MOD_ID);
    public static RegistryObject<MobEffect> MOLTEN_ARMOR = EFFECTS.register("molten_armor",
            MoltenArmorEffect::new);
    public static RegistryObject<MobEffect> INCINERATION = EFFECTS.register("incineration",
            IncinerationEffect::new);
}
