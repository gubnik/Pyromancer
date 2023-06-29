package net.team_prometheus.pyromancer.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.init.ModAttributes;
@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = PyromancerMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModAttributeProvider {
    @SubscribeEvent
    public static void entityAttributeProvider(EntityAttributeModificationEvent event){
        event.add(EntityType.PLAYER, ModAttributes.BLAZE_CONSUMPTION.get());
        event.add(EntityType.PLAYER, ModAttributes.PYROMANCY_DAMAGE.get());
    }
}
