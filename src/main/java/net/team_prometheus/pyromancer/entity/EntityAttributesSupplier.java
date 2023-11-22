package net.team_prometheus.pyromancer.entity;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.entity.unburned.Unburned;
import net.team_prometheus.pyromancer.registries.ModEntities;

@Mod.EventBusSubscriber(modid = PyromancerMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityAttributesSupplier {
    @SubscribeEvent
    public static void entityAttributeRegister(EntityAttributeCreationEvent event){
        event.put(ModEntities.UNBURNED.get(), Unburned.setAttributes());
    }
}
