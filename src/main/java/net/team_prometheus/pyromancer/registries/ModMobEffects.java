package net.team_prometheus.pyromancer.registries;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.mob_effects.IncinerationEffect;
import net.team_prometheus.pyromancer.mob_effects.MoltenArmorEffect;
import net.team_prometheus.pyromancer.mob_effects.coating_effects.MercuryCoatingEffect;

public class ModMobEffects {
    public static DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, PyromancerMod.MOD_ID);
    public static RegistryObject<MobEffect> MOLTEN_ARMOR = EFFECTS.register("molten_armor",
            MoltenArmorEffect::new);
    public static RegistryObject<MobEffect> INCINERATION = EFFECTS.register("incineration",
            IncinerationEffect::new);
    public static RegistryObject<MobEffect> MERCURY_COATING = EFFECTS.register("mercury_coating",
            MercuryCoatingEffect::new);
}
