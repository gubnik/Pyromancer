package net.team_prometheus.pyromancer.registries;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.entity.PyromancerBoatEntity;
import net.team_prometheus.pyromancer.entity.PyromancerChestBoatEntity;
import net.team_prometheus.pyromancer.entity.attack_effects.FirePillarEntity;
import net.team_prometheus.pyromancer.entity.projectiles.Bombsack;
import net.team_prometheus.pyromancer.entity.projectiles.NapalmBombsack;
import net.team_prometheus.pyromancer.entity.projectiles.SizzlingHandFireball;
import net.team_prometheus.pyromancer.entity.projectiles.SparkProjectile;
import net.team_prometheus.pyromancer.entity.unburned.Unburned;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PyromancerMod.MOD_ID);
    public static final RegistryObject<EntityType<PyromancerBoatEntity>> BOAT = register("boat",
            EntityType.Builder.<PyromancerBoatEntity>of(PyromancerBoatEntity::new, MobCategory.MISC)
                    .sized(1.375F, 0.5625F).clientTrackingRange(10)
                    .fireImmune());
    public static final RegistryObject<EntityType<PyromancerChestBoatEntity>> CHEST_BOAT = register("chest_boat",
            EntityType.Builder.<PyromancerChestBoatEntity>of(PyromancerChestBoatEntity::new, MobCategory.MISC)
                    .sized(1.375F, 0.5625F).clientTrackingRange(10)
                    .fireImmune());
    public static final RegistryObject<EntityType<Unburned>> UNBURNED = register("unburned",
            EntityType.Builder.<Unburned>of(Unburned::new, MobCategory.MONSTER)
                    .sized(1.3f, 5.2f).fireImmune());

    // projectiles

    public static final RegistryObject<EntityType<SizzlingHandFireball>> SIZZLING_HAND_FIREBALL = register("sizzling_hand_fireball",
            EntityType.Builder.<SizzlingHandFireball>of(SizzlingHandFireball::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));
    public static final RegistryObject<EntityType<Bombsack>> BOMBSACK = register("bombsack",
            EntityType.Builder.<Bombsack>of(Bombsack::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10));
    public static final RegistryObject<EntityType<NapalmBombsack>> NAPALM_BOMBSACK = register("napalm_bombsack",
            EntityType.Builder.<NapalmBombsack>of(NapalmBombsack::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10));
    public static final RegistryObject<EntityType<SparkProjectile>> SPARK = register("spark",
            EntityType.Builder.<SparkProjectile>of(SparkProjectile::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(128).setShouldReceiveVelocityUpdates(true));

    // ATTACK EFFECTS

    public static final RegistryObject<EntityType<FirePillarEntity>> FIRE_PILLAR = register("fire_pillar",
            EntityType.Builder.<FirePillarEntity>of(FirePillarEntity::new, MobCategory.MISC)
                    .clientTrackingRange(128).setShouldReceiveVelocityUpdates(false));


    private static <T extends Entity> RegistryObject<EntityType<T>> register(String registry_name, EntityType.Builder<T> entityTypeBuilder) {
        return ENTITIES.register(registry_name, () -> entityTypeBuilder.build(registry_name));
    }
}
