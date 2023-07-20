package net.team_prometheus.pyromancer.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FunnyMagicWorks {
    private final String animId;

    public FunnyMagicWorks(String animId) {
        this.animId = animId;
    }

    public FunnyMagicWorks(FriendlyByteBuf buf) {
        this.animId = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(animId);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> NetworkCore.sendToPlayersNearbyAndSelf(new AnimationsNetwork(this.animId, context.getSender().getId()), context.getSender()));
    }
}
