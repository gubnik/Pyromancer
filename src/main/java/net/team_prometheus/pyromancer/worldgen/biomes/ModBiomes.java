package net.team_prometheus.pyromancer.worldgen.biomes;

import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBiomes {
    public static DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, PyromancerMod.MOD_ID);
    public static RegistryObject<Biome> FLAMING_GROVE = BIOMES.register("flaming_grove",
            ModNetherBiomes::flaming_grove);
}
