package net.team_prometheus.pyromancer.items.coating;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class CoatingItem extends Item {
    private final MobEffect coatingEffect;
    public CoatingItem(Properties properties, MobEffect coatingEffect) {
        super(properties);
        this.coatingEffect = coatingEffect;
    }
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand interactionHand){
        return InteractionResultHolder.success(player.getItemInHand(interactionHand));
    }
    @Override
    public int getUseDuration(@NotNull ItemStack itemstack) {
        return 10;
    }
    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack){
        return UseAnim.CROSSBOW;
    }
    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, LivingEntity entity) {
        itemStack.shrink(1);
        entity.addEffect(new MobEffectInstance(this.coatingEffect, 1200, 0));
        return itemStack;
    }
}
