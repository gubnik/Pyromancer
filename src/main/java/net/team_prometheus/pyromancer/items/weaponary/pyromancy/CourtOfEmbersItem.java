package net.team_prometheus.pyromancer.items.weaponary.pyromancy;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.team_prometheus.pyromancer.entity.EntityUtils;
import net.team_prometheus.pyromancer.entity.ModEntities;
import net.team_prometheus.pyromancer.entity.projectiles.SizzlingHandFireball;
import net.team_prometheus.pyromancer.items.ItemUtils;
import org.jetbrains.annotations.NotNull;
public class CourtOfEmbersItem extends PyromancyItem{
    public CourtOfEmbersItem(Properties properties) {
        super(properties, 2, 2);
        this.SHRINK = false;
    }
    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemstack) {
        return UseAnim.SPEAR;
    }
    @Override
    public int getUseDuration(@NotNull ItemStack itemstack) {
        return 72000;
    }
    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity entity, @NotNull ItemStack itemStack, int time) {
        if(time % 3 == 0){
            if (entity instanceof Player player){
                EntityUtils.shootProjectile(new SizzlingHandFireball(ModEntities.SIZZLING_HAND_FIREBALL.get(), level).setParameters(1.5f, 10),
                        player, 1.5f, 0.3f);
                ItemUtils.changeBlaze(player, -1 * getBlazeCost(entity));
            }
        }
    }
}
