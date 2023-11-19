package net.team_prometheus.pyromancer.items.weaponary.pyromancy;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.team_prometheus.pyromancer.init.ModAttributes;
import net.team_prometheus.pyromancer.items.blazing_journal.BlazingJournal;
import org.jetbrains.annotations.NotNull;

public class PyromancyItem extends Item {
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
        int blazeConsumption = (int) entity.getAttributeValue(ModAttributes.BLAZE_CONSUMPTION.get());
        if(hand.equals(InteractionHand.MAIN_HAND)
                && entity.getOffhandItem().getItem() instanceof BlazingJournal
                && entity.getOffhandItem().getOrCreateTag().getInt("blaze") > blazeConsumption){
            entity.startUsingItem(hand);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, entity.getItemInHand(hand));
        } else return new InteractionResultHolder<>(InteractionResult.FAIL, entity.getItemInHand(hand));
    }
    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemstack) {
        return UseAnim.NONE;
    }
    @Override
    public int getUseDuration(@NotNull ItemStack itemstack) {
        return 0;
    }
    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity entity, @NotNull ItemStack itemStack, int time) {
    }
    public InteractionResultHolder<ItemStack> compendiumUse(@NotNull Level world, @NotNull Player entity, @NotNull InteractionHand hand){
        int blazeConsumption = getBlazeCost(entity);
        ItemStack journal = (entity.getOffhandItem().getItem() instanceof BlazingJournal) ? entity.getOffhandItem() : entity.getMainHandItem();
        if(hand.equals(InteractionHand.MAIN_HAND)){
            if(journal.getOrCreateTag().getInt("blaze") > blazeConsumption) {
                entity.startUsingItem(hand);
                return new InteractionResultHolder<>(InteractionResult.SUCCESS, entity.getItemInHand(hand));
            } else return new InteractionResultHolder<>(InteractionResult.FAIL, entity.getItemInHand(hand));
        } else return new InteractionResultHolder<>(InteractionResult.FAIL, entity.getItemInHand(hand));
    }
    public int getBlazeCost(LivingEntity entity){
        return (int) entity.getAttributeValue(ModAttributes.BLAZE_CONSUMPTION.get());
    }
}
