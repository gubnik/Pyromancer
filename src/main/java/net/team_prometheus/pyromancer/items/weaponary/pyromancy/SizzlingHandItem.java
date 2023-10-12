package net.team_prometheus.pyromancer.items.weaponary.pyromancy;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.team_prometheus.pyromancer.entity.EntityUtils;
import net.team_prometheus.pyromancer.entity.ModEntities;
import net.team_prometheus.pyromancer.entity.projectiles.SizzlingHandFireball;
import net.team_prometheus.pyromancer.init.ModAttributes;
import net.team_prometheus.pyromancer.items.ItemUtils;
import net.team_prometheus.pyromancer.items.blazing_journal.BlazingJournal;
import org.jetbrains.annotations.NotNull;
@SuppressWarnings("deprecation")
public class SizzlingHandItem extends PyromancyItem {
    public SizzlingHandItem(Properties properties) {
        super(properties, 1, 4);
    }
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player entity, @NotNull InteractionHand hand) {
        this.blazeConsumption = (int) entity.getAttributeValue(ModAttributes.BLAZE_CONSUMPTION.get());
        if(hand.equals(InteractionHand.MAIN_HAND)
        && entity.getOffhandItem().getItem() instanceof BlazingJournal
                && entity.getOffhandItem().getOrCreateTag().getInt("blaze") > blazeConsumption){
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
            if (entity instanceof Player player){
                EntityUtils.shootProjectile(new SizzlingHandFireball(ModEntities.SIZZLING_HAND_FIREBALL.get(), level).setParameters(1.5f, 10),
                        player, 1.5f, 0.1f);
                ItemUtils.changeBlaze(player, -1 * blazeConsumption);
            }
        }
    }
}
