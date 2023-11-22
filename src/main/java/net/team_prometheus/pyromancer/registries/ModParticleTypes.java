package net.team_prometheus.pyromancer.registries;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;

public class ModParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, PyromancerMod.MOD_ID);
    public static final RegistryObject<ParticleType<SimpleParticleType>> ROT = PARTICLES.register("rot_particle",
            ()-> new SimpleParticleType(true));
}
