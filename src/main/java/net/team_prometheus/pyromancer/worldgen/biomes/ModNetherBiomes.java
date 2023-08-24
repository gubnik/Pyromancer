package net.team_prometheus.pyromancer.worldgen.biomes;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.worldgen.carvers.ModCarvers;
import net.team_prometheus.pyromancer.worldgen.features.ModNetherPlacements;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

public class ModNetherBiomes {
    // terrablender
    public static void setupTerraBlender(){
        Regions.register(new ModNetherRegion(6));
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.NETHER, PyromancerMod.MOD_ID, ModNetherSurfaceRules.nether());
    }
    public static Biome flaming_grove(){
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder();
        biomeBuilder.addCarver(GenerationStep.Carving.AIR, ModCarvers.FLAMING_GROVE_CARVER.getHolder().get());
        biomeBuilder
                .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModNetherPlacements.PYROWOOD_NETHER.getHolder().orElseThrow())
                .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModNetherPlacements.FLAMING_GROVE_VEGETATION.getHolder().orElseThrow())
                .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModNetherPlacements.SIZZLING_VINES.getHolder().orElseThrow());
        BiomeDefaultFeatures.addNetherDefaultOres(biomeBuilder);
        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.NONE).temperature(2f).downfall(0f)
                .specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).foliageColorOverride(-6649233).grassColorOverride(-6649233).skyColor(-12840192).fogColor(-12840192).ambientParticle(new AmbientParticleSettings(ParticleTypes.FLAME, 0.01f)).build())
                .mobSpawnSettings(spawnBuilder.build()).generationSettings(biomeBuilder.build())
                .build();
    }
}
