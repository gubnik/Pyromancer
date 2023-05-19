package net.team_prometheus.pyromancer;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.team_prometheus.pyromancer.blocks.ModBlocks;
import net.team_prometheus.pyromancer.init.ModAttributes;
import net.team_prometheus.pyromancer.init.ModEnchantments;
import net.team_prometheus.pyromancer.items.ModItems;
import org.slf4j.Logger;

import java.util.UUID;

@Mod(PyromancerMod.MOD_ID)
public class PyromancerMod {
    public static final String MOD_ID = "pyromancer";
    public static final UUID BASE_ARMOR_TOUGHNESS_UUID = UUID.fromString("52274c52-f4c7-11ed-a05b-0242ac120003");
    public static final UUID BLAZE_CONSUMPTION_UUID = UUID.fromString("39f6d6b6-f4f9-11ed-a05b-0242ac120003");
    private static final Logger LOGGER = LogUtils.getLogger();
    public PyromancerMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEnchantments.ENCHANTMENTS.register(modEventBus);
        ModAttributes.ATTRIBUTES.register(modEventBus);
        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }
    private void commonSetup(final FMLCommonSetupEvent event) {
    }
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}
