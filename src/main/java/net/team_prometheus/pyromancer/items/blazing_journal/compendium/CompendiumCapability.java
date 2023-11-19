package net.team_prometheus.pyromancer.items.blazing_journal.compendium;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import net.team_prometheus.pyromancer.items.weaponary.pyromancy.PyromancyItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class   CompendiumCapability implements ICapabilitySerializable<CompoundTag> {
    private static final int MAX_ITEMS = CompendiumOfFlame.MAX_ITEMS;
    private final LazyOptional<ItemStackHandler> inventory = LazyOptional.of(this::createItemHandler);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == ForgeCapabilities.ITEM_HANDLER ? this.inventory.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = getItemHandler().serializeNBT();
        tag.putInt("active_slot", 0);
        tag.putDouble("CustomModelData", 0);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getItemHandler().deserializeNBT(nbt);
    }

    private ItemStackHandler createItemHandler() {
        return new ItemStackHandler(MAX_ITEMS) {
            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() instanceof PyromancyItem;
            }

            @Override
            public void setSize(int size) {
            }
        };
    }
    private ItemStackHandler getItemHandler() {
        return inventory.orElseThrow(RuntimeException::new);
    }

}
