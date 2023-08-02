package net.team_prometheus.pyromancer.animations;

import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.resources.ResourceLocation;
import net.team_prometheus.pyromancer.PyromancerMod;

public class AnimationList {
    public static KeyframeAnimation SOULFLAME_IGNITION(){
        return PlayerAnimationRegistry.getAnimation(new ResourceLocation(PyromancerMod.MOD_ID, "soulflame_ignition"));
    }
    public static KeyframeAnimation ASHEN_FORM(){
        return PlayerAnimationRegistry.getAnimation(new ResourceLocation(PyromancerMod.MOD_ID, "ashen_form"));
    }
    public static KeyframeAnimation AEGIS_OF_FIRE(){
        return PlayerAnimationRegistry.getAnimation(new ResourceLocation(PyromancerMod.MOD_ID, "aegis_of_fire"));
    }
    public static KeyframeAnimation HEAVENLY_FLAME(){
        return PlayerAnimationRegistry.getAnimation(new ResourceLocation(PyromancerMod.MOD_ID, "heavenly_flame"));
    }
    public static KeyframeAnimation TORNADO_OF_SOULS(){
        return PlayerAnimationRegistry.getAnimation(new ResourceLocation(PyromancerMod.MOD_ID, "tornado_of_souls"));
    }
}
