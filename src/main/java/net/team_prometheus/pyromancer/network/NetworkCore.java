package net.team_prometheus.pyromancer.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.items.embers.Ember;
import net.team_prometheus.pyromancer.items.embers.EmbersApplication;

import java.util.function.Supplier;

public class NetworkCore {
    private static SimpleChannel INSTANCE;
    private static int id;
    public static void register() {
        SimpleChannel network = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(PyromancerMod.MOD_ID, "messages")).networkProtocolVersion(() -> "1.0").clientAcceptedVersions(s -> true).serverAcceptedVersions(s -> true).simpleChannel();
        INSTANCE = network;
        network.messageBuilder(AnimationsNetwork.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(AnimationsNetwork::new)
                .encoder(AnimationsNetwork::toBytes)
                .consumerMainThread(AnimationsNetwork::handle)
                .add();
        network.messageBuilder(FunnyMagicWorks.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(FunnyMagicWorks::new)
                .encoder(FunnyMagicWorks::toBytes)
                .consumerMainThread(FunnyMagicWorks::handle)
                .add();
    }
    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }
    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
    public static <MSG> void sendToPlayersNearby(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> player), message);
    }
    public static <MSG> void sendToPlayersNearbyAndSelf(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), message);
    }
    public static <MSG> void sendToAll(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
