package net.team_prometheus.pyromancer.init;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;

public class ModAttributes {
    public static DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, PyromancerMod.MOD_ID);
    public static final RegistryObject<Attribute> BLAZE_CONSUMPTION = ATTRIBUTES.register("generic.blaze_consumption", () -> new RangedAttribute("attribute.name.generic.blaze_consumption", 0f, 0f, 512f));
    public static final RegistryObject<Attribute> PYROMANCY_DAMAGE = ATTRIBUTES.register("generic.pyromancy_damage", () -> new RangedAttribute("attribute.name.generic.pyromancy_damage", 0f, 0f, 666f));
}
