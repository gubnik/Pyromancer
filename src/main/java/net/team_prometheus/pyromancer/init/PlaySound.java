package net.team_prometheus.pyromancer.init;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class PlaySound {
    public static void run(String name, SoundSource soundSource, float loudness, float pitch, Level level, double x, double y, double z, @Nullable Player player){
        if (!level.isClientSide()) {
            level.playSound(player, new BlockPos(x, y, z), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(name))), soundSource, loudness, pitch);
        } else {
            level.playLocalSound(x, y, z, Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(name))), soundSource, loudness, pitch, false);
        }
    }
}
