package net.team_prometheus.pyromancer.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.entity.projectiles.Bombsack;
import net.team_prometheus.pyromancer.entity.projectiles.NapalmBombsack;
import net.team_prometheus.pyromancer.entity.projectiles.SizzlingHandFireball;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PyromancerMod.MOD_ID);
    public static final RegistryObject<EntityType<SizzlingHandFireball>> SIZZLING_HAND_FIREBALL = register("sizzling_hand_fireball",
            EntityType.Builder.<SizzlingHandFireball>of(SizzlingHandFireball::new, MobCategory.MISC)
                    .setCustomClientFactory(SizzlingHandFireball::new).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10));
    public static final RegistryObject<EntityType<Bombsack>> BOMBSACK = register("bombsack",
            EntityType.Builder.<Bombsack>of(Bombsack::new, MobCategory.MISC)
                    .setCustomClientFactory(Bombsack::new).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10));
    public static final RegistryObject<EntityType<NapalmBombsack>> NAPALM_BOMBSACK = register("napalm_bombsack",
            EntityType.Builder.<NapalmBombsack>of(NapalmBombsack::new, MobCategory.MISC)
                    .setCustomClientFactory(NapalmBombsack::new).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10));
    private static <T extends Entity> RegistryObject<EntityType<T>> register(String registry_name, EntityType.Builder<T> entityTypeBuilder) {
        return ENTITIES.register(registry_name, () -> entityTypeBuilder.build(registry_name));
    }
}
