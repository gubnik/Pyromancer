package net.team_prometheus.pyromancer.items.embers;

import dev.kosmx.playerAnim.api.TransformType;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonConfiguration;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.event.ItemStackedOnOtherEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;
import net.team_prometheus.pyromancer.init.SetupAnimations;
import net.team_prometheus.pyromancer.items.MaceItem;
import net.team_prometheus.pyromancer.items.ModItems;
import net.team_prometheus.pyromancer.network.animations.GetAnimationFromServer;
import net.team_prometheus.pyromancer.network.NetworkCore;

import java.util.concurrent.atomic.AtomicReference;
@Mod.EventBusSubscriber
public class EmberUtilities {
    @SubscribeEvent
    @SuppressWarnings("unused")
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
                && emberTag.equals("") && !hoverEmberTag.equals("")
        && Ember.byName(hoverEmberTag).getWeaponType().getWeapon().isInstance(itemStack.getItem())
        ) {
            event.setCanceled(event.isCancelable());
            itemStack.getOrCreateTag().putString("ember", hoverEmberTag);
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
        NetworkCore.sendToServer(new GetAnimationFromServer(ember.getName()));
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
    //@SubscribeEvent
    //@SuppressWarnings("unused")
    //public static void attackCancel(AttackEntityEvent event){
    //    if(!event.getEntity().getMainHandItem().getOrCreateTag().getString("ember").equals("")
    //            && event.getEntity().getCooldowns().isOnCooldown(event.getEntity().getMainHandItem().getItem())){
    //        event.setCanceled(event.isCancelable());
    //    }
    //}
}
