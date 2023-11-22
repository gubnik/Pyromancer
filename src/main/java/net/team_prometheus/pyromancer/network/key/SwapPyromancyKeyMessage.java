package net.team_prometheus.pyromancer.network.key;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.team_prometheus.pyromancer.items.compendium.CompendiumOfFlame;

import java.util.function.Supplier;

public class SwapPyromancyKeyMessage {
    int type, pressedms;

    public SwapPyromancyKeyMessage(int type, int pressedms) {
        this.type = type;
        this.pressedms = pressedms;
    }

    public SwapPyromancyKeyMessage(FriendlyByteBuf buffer) {
        this.type = buffer.readInt();
        this.pressedms = buffer.readInt();
    }

    public static void toBytes(SwapPyromancyKeyMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.type);
        buffer.writeInt(message.pressedms);
    }

    public static void handle(SwapPyromancyKeyMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> pressAction(context.getSender(), message.type, message.pressedms));
        context.setPacketHandled(true);
    }

    public static void pressAction(Player entity, int type, int pressedms) {
        if(entity.getMainHandItem().getItem() instanceof CompendiumOfFlame){
            ItemStack compendium = entity.getMainHandItem();
            CompoundTag nbt = compendium.getOrCreateTag();
            nbt.putInt("active_slot", (nbt.getInt("active_slot") >= CompendiumOfFlame.MAX_ITEMS - 1) ? 0 : nbt.getInt("active_slot") + 1);
        }
    }
}
