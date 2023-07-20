package net.team_prometheus.pyromancer.items.embers;

import dev.kosmx.playerAnim.api.AnimUtils;
import dev.kosmx.playerAnim.api.TransformType;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonConfiguration;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.event.ItemStackedOnOtherEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.init.SetupAnimations;
import net.team_prometheus.pyromancer.items.ItemUtils;
import net.team_prometheus.pyromancer.items.MaceItem;
import net.team_prometheus.pyromancer.items.ModItems;
import net.team_prometheus.pyromancer.network.FunnyMagicWorks;
import net.team_prometheus.pyromancer.network.NetworkCore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber
public class EmbersApplication {
    @SubscribeEvent
    public static void applier(ItemStackedOnOtherEvent event){
        ItemStack carriedItemstack = event.getCarriedItem();
        ItemStack itemStack = event.getStackedOnItem();
        String emberTag = itemStack.getOrCreateTag().getString("ember");
        String hoverEmberTag = carriedItemstack.getOrCreateTag().getString("ember");
        if(event.getClickAction() == ClickAction.SECONDARY
                && carriedItemstack.getItem().equals(ModItems.EMBER.get())
                && (itemStack.getItem() instanceof SwordItem
                || itemStack.getItem() instanceof AxeItem
                || itemStack.getItem() instanceof MaceItem)
                && emberTag.equals("") && carriedItemstack.getOrCreateTag().getString("ember").equals("")
        //&& Ember.byName(hoverEmberTag).getWeaponType().getWeaponType().isInstance(itemStack.getItem())
        ) {
            event.setCanceled(event.isCancelable());
            itemStack.getOrCreateTag().putString("ember", Ember.SOULFLAME_IGNITION.getName());
        }
    }
    @SubscribeEvent
    public static void emberUsed(PlayerInteractEvent.RightClickItem event){
        ItemStack itemStack = event.getItemStack();
        String emberTag = itemStack.getOrCreateTag().getString("ember");
        if(!emberTag.equals("")) {
            Player player = event.getEntity();
            Ember ember = Ember.byName(emberTag);
            if (itemStack.getItem().getUseDuration(itemStack) == 0 && ember.getWeaponType().getWeapon().isInstance(itemStack.getItem())) {
                PyromancerMod.queueServerWork(ember.getAnimationDelay(), () -> {
                    ember.getFunction().apply(player);
                    emberCooldown(player, ember.getCooldown(), emberTag);});
                playAnimation(ember);
            }
        }
    }
    public static void emberCooldown(Player player, int time, String tag){
        AtomicReference<IItemHandler> itemHandReference = new AtomicReference<>();
        player.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(itemHandReference::set);
        if (itemHandReference.get() != null) {
            for (int i = 0; i < itemHandReference.get().getSlots(); i++) {
                ItemStack itemStack = itemHandReference.get().getStackInSlot(i).copy();
                if (itemStack.getOrCreateTag().getString("ember").equals(tag)) {
                    player.getCooldowns().addCooldown(itemStack.getItem(), time);
                }
            }
        }
    }
    public static void playAnimation(Ember ember){
        NetworkCore.sendToServer(new FunnyMagicWorks(ember.getName()));
    }
    public static void playAnimation(AbstractClientPlayer player, Ember ember){
        if (SetupAnimations.animationData.get(player) != null) {
            SetupAnimations.animationData.get(player).replaceAnimationWithFade(new AbstractFadeModifier(2) {
                @Override protected float getAlpha(String modelName, TransformType type, float progress) {return 0.2f;}
            }, new KeyframeAnimationPlayer(ember.getAnimation())
                    .setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL).
                    setFirstPersonConfiguration(new FirstPersonConfiguration()
                            .setShowLeftArm(true)
                            .setShowLeftItem(true)
                            .setShowRightArm(true)
                            .setShowRightItem(true)
                    ));
        }
    }
}
