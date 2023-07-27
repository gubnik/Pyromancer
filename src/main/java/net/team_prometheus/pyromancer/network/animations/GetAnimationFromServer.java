package net.team_prometheus.pyromancer.network.animations;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.team_prometheus.pyromancer.network.NetworkCore;

import java.util.Objects;
import java.util.function.Supplier;

public class GetAnimationFromServer {
    private final String animId;

    public GetAnimationFromServer(String animId) {
        this.animId = animId;
    }

    public GetAnimationFromServer(FriendlyByteBuf buf) {
        this.animId = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(animId);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> NetworkCore.sendToPlayersNearbyAndSelf(new SendAnimationsToServer(this.animId, Objects.requireNonNull(context.getSender()).getId()), context.getSender()));
    }
}
