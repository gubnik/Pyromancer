package net.team_prometheus.pyromancer.blaze;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerBlazeProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerBlaze> PLAYER_BLAZE = CapabilityManager.get(new CapabilityToken<PlayerBlaze>() {});
    private PlayerBlaze blaze = null;
    private final LazyOptional<PlayerBlaze> optional = LazyOptional.of(this::createPlayerBlaze);

    private PlayerBlaze createPlayerBlaze() {
        if(this.blaze == null) this.blaze = new PlayerBlaze();
        return this.blaze;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_BLAZE) return optional.cast();
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerBlaze().writeNbt(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}
