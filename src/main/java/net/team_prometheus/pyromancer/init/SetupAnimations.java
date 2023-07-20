package net.team_prometheus.pyromancer.init;

import dev.kosmx.playerAnim.api.layered.AnimationStack;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.team_prometheus.pyromancer.PyromancerMod;

import java.util.IdentityHashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = PyromancerMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SetupAnimations {
    public static final Map<AbstractClientPlayer, ModifierLayer<IAnimation>> animationData = new IdentityHashMap<>();
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        PlayerAnimationAccess.REGISTER_ANIMATION_EVENT.register(SetupAnimations::registerPlayerAnimation);
    }
    private static void registerPlayerAnimation(AbstractClientPlayer player, AnimationStack stack) {
        var layer = new ModifierLayer<>();
        stack.addAnimLayer(1000, layer); //Register the layer with a priority
        SetupAnimations.animationData.put(player, layer);
    }
}
