package net.team_prometheus.pyromancer.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.team_prometheus.pyromancer.entity.ModEntities;
import net.team_prometheus.pyromancer.entity.projectiles.SizzlingHandFireball;
import org.jetbrains.annotations.NotNull;

public class SizzlingHand extends PyromancyItem {
    private final int blazeConsumption ;
    public SizzlingHand(int blazeCost, int uses, Properties properties) {
        super(blazeCost, uses, properties);
        this.blazeConsumption = blazeCost;
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
            if (!level.isClientSide
                    //&& journal.getOrCreateTag().getInt("blaze") >= this.blazeConsumption
            ) {
                //
                SizzlingHandFireball fireball = new SizzlingHandFireball(ModEntities.SIZZLING_HAND_FIREBALL.get(), level);
                fireball.setOwner(entity);
                fireball.setPos(entity.getX(), entity.getEyeY() - 0.3, entity.getZ());
                //
                fireball.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 1.5f, 0.1f);
                level.addFreshEntity(fireball);
                journal.getOrCreateTag().putInt("blaze",
                        journal.getOrCreateTag().getInt("blaze") - this.blazeConsumption);
            }
        }
    }
}
