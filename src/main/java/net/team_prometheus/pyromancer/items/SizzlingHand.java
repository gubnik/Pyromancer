package net.team_prometheus.pyromancer.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeItem;
import net.team_prometheus.pyromancer.entity.EntityUtils;
import net.team_prometheus.pyromancer.entity.ModEntities;
import net.team_prometheus.pyromancer.entity.projectiles.SizzlingHandFireball;
import org.jetbrains.annotations.NotNull;

public class SizzlingHand extends PyromancyItem {
    public final int blazeConsumption ;
    public final int damage;
    public SizzlingHand(int damage, int blazeCost, int uses, Properties properties) {
        super(damage, blazeCost, uses, properties);
        this.blazeConsumption = blazeCost;
        this.damage = damage;
    }
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player entity, @NotNull InteractionHand hand) {
        if(hand.equals(InteractionHand.MAIN_HAND)
        && entity.getOffhandItem().getItem() instanceof BlazingJournal && entity.getOffhandItem().getOrCreateTag().getInt("blaze") > blazeConsumption){
            entity.startUsingItem(hand);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, entity.getItemInHand(hand));
        } else return new InteractionResultHolder<>(InteractionResult.FAIL, entity.getItemInHand(hand));
    }
    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemstack) {
        return UseAnim.BOW;
    }
    @Override
    public int getUseDuration(@NotNull ItemStack itemstack) {
        return 72000;
    }
    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity entity, @NotNull ItemStack itemStack, int time) {
        if(entity.getOffhandItem().getItem() instanceof BlazingJournal && time % 3 == 0){
            ItemStack journal = entity.getOffhandItem();
            if (!level.isClientSide){
                if(entity instanceof Player player) {
                    EntityUtils.shootPyromancyFireballProjectile(new SizzlingHandFireball(ModEntities.SIZZLING_HAND_FIREBALL.get(), level),
                            1.5f, 0.1f, 10, player);
                    ItemUtils.changeBlaze(player,
                            -1 * ItemUtils.getBlazeConsumption(player));
                }
            }
        }
    }
}
