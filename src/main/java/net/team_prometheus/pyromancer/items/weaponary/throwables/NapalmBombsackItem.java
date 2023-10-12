package net.team_prometheus.pyromancer.items.weaponary.throwables;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.team_prometheus.pyromancer.entity.ModEntities;
import net.team_prometheus.pyromancer.entity.projectiles.NapalmBombsack;
import org.jetbrains.annotations.NotNull;

public class NapalmBombsackItem extends Item {
    public NapalmBombsackItem(Properties properties) {
        super(properties);
    }
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!level.isClientSide) {
            NapalmBombsack bombsack = new NapalmBombsack(ModEntities.NAPALM_BOMBSACK.get(), level);
            bombsack.setItem(itemstack);
            bombsack.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
            bombsack.shoot(player.getLookAngle().x, player.getLookAngle().y, player.getLookAngle().z, 0.8f, 0.1f);
            level.addFreshEntity(bombsack);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
