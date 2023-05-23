package net.team_prometheus.pyromancer.entity;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEntityRenderers {
    @SubscribeEvent
    public static void registerEntityRender(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(ModEntities.SIZZLING_HAND_FIREBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.BOMBSACK.get(), ThrownItemRenderer::new);
    }
}
