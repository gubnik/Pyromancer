package net.team_prometheus.pyromancer.items.weaponary.pyromancy;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.extensions.IForgeItem;
import net.team_prometheus.pyromancer.registries.ModAttributes;
import net.team_prometheus.pyromancer.items.blazing_journal.BlazingJournalItem;
import net.team_prometheus.pyromancer.util.ItemUtils;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class PyromancyItem extends Item implements IClientItemExtensions, IForgeItem {
    public boolean SHRINK = false;
    public final int blazeConsumptionModifier;
    public final int pyromancyDamageModifier;
    public PyromancyItem(Properties properties, int blazeConsumptionModifier, int pyromancyDamageModifier) {
        super(properties);
        this.blazeConsumptionModifier = blazeConsumptionModifier;
        this.pyromancyDamageModifier = pyromancyDamageModifier;
    }
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player entity, @NotNull InteractionHand hand) {
        if(hand.equals(InteractionHand.MAIN_HAND)
                && entity.getOffhandItem().getItem() instanceof BlazingJournalItem
                && entity.getOffhandItem().getOrCreateTag().getInt("blaze") > getBlazeCost(entity)){
            entity.startUsingItem(hand);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, entity.getItemInHand(hand));
        } else return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(hand));
    }
    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemstack) {
        return UseAnim.CUSTOM;
    }
    @Override
    public int getUseDuration(@NotNull ItemStack itemstack) {
        return 0;
    }
    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity entity, @NotNull ItemStack itemStack, int time) {
        if(!(entity instanceof Player player)) return;
        if(ItemUtils.getBlaze(player) < getBlazeCost(entity)) entity.stopUsingItem();
    }
    public InteractionResultHolder<ItemStack> compendiumUse(@NotNull Level world, @NotNull Player entity, @NotNull InteractionHand hand){
        int blazeConsumption = getBlazeCost(entity);
        ItemStack journal = (entity.getOffhandItem().getItem() instanceof BlazingJournalItem) ? entity.getOffhandItem() : entity.getMainHandItem();
        if(hand.equals(InteractionHand.MAIN_HAND)){
            if(journal.getOrCreateTag().getInt("blaze") > blazeConsumption) {
                entity.startUsingItem(hand);
                return new InteractionResultHolder<>(InteractionResult.SUCCESS, entity.getItemInHand(hand));
            } else return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(hand));
        } else return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(hand));
    }
    public int getBlazeCost(LivingEntity entity){
        return (int) entity.getAttributeValue(ModAttributes.BLAZE_CONSUMPTION.get());
    }
    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer){
        consumer.accept(new PyromancyClientExtension());
    }
    public static class PyromancyClientExtension implements IClientItemExtensions {
        public static HumanoidModel.ArmPose CAST = HumanoidModel.ArmPose.create("cast", false, (model, entity, arm) -> {
            int tick;
            if(entity instanceof Player player) tick = player.getUseItemRemainingTicks();
            else return;
            if(tick <= 0) return;
            ModelPart armPart = (arm.equals(HumanoidArm.RIGHT)) ? model.rightArm : model.leftArm;
            armPart.xRot = -1 * Mth.cos( (float) Math.PI * tick * 9 / 180) / 2 + model.head.xRot - (float) Math.PI / 2;
            armPart.zRot = Mth.cos( (float) Math.PI * tick * 9 / 180);
        });
        public static HumanoidModel.ArmPose SKY_SUMMON = HumanoidModel.ArmPose.create("sky_summon", false, (model, entity, arm) -> {
            int tick;
            if(entity instanceof Player player) tick = player.getUseItemRemainingTicks();
            else return;
            if(tick <= 0) return;
            ModelPart armPart = (arm.equals(HumanoidArm.RIGHT)) ? model.rightArm : model.leftArm;
            armPart.xRot = (float) Math.PI - Mth.sin( (float) Math.PI * tick * 9 / 180) * -1 * Mth.cos( (float) Math.PI * tick * 9 / 180);
            armPart.zRot = -1 * Mth.cos( (float) Math.PI * tick * 9 / 180) / 5;
        });
        //public static HumanoidModel.ArmPose VOID_NOTHING = HumanoidModel.ArmPose.create("void_nothing", false, (model, entity, arm) -> {
        //});
        @Override
        public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
            if(entityLiving instanceof Player player && player.getUseItemRemainingTicks() <= 0) return HumanoidModel.ArmPose.EMPTY;
            return switch (itemStack.getItem().toString()){
                case("sizzling_hand") -> CAST;
                case("court_of_embers") -> SKY_SUMMON;
                default -> HumanoidModel.ArmPose.EMPTY;
            };
        }
        @Override
        public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
            return false; //(player.getUseItemRemainingTicks() > 0);
        }
    }
}
