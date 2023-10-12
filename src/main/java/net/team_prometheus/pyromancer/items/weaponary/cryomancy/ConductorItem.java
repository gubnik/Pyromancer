package net.team_prometheus.pyromancer.items.weaponary.cryomancy;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.team_prometheus.pyromancer.damage_source.ModDamageSource;
import net.team_prometheus.pyromancer.entity.EntityUtils;
import net.team_prometheus.pyromancer.init.ModTabs;
import net.team_prometheus.pyromancer.init.PyromancerConfig;
import net.team_prometheus.pyromancer.items.ItemUtils;
import net.team_prometheus.pyromancer.items.blazing_journal.BlazingJournal;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class ConductorItem extends CryomancyItem{
    public ConductorItem() {
        super(new Properties().tab(ModTabs.PYROMANCER_TAB).stacksTo(1), 1, 2f);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player entity, @NotNull InteractionHand hand){

        int journalMaxCapacity = PyromancerConfig.COMMON.journalMaxCapacity.get();

        if(entity.getOffhandItem().getItem() instanceof BlazingJournal){
            ItemStack journal = entity.getOffhandItem();

            if(journal.getOrCreateTag().getInt("blaze") <= journalMaxCapacity - blazeRegained) {
                entity.startUsingItem(hand);
                return new  InteractionResultHolder<>(InteractionResult.SUCCESS, entity.getItemInHand(hand));
            } else return new  InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(hand));
        } else return InteractionResultHolder.pass(entity.getItemInHand(hand));
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemstack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemstack) {
        return 1000;
    }

    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity entity, @NotNull ItemStack itemStack, int time) {
        float rx, ry, rz;
        double ex, ey, ez;
        rx = ThreadLocalRandom.current().nextFloat(-12, 13);
        ry = ThreadLocalRandom.current().nextFloat(-12, 13);
        rz = ThreadLocalRandom.current().nextFloat(-12, 13);
        if(entity instanceof Player player) {
            ex = entity.getX();
            ey = entity.getY();
            ez = entity.getZ();
            if (level instanceof ServerLevel serverLevel) {
                conductorParticle(this.damage, 2, new Vec3(ex + rx, ey + ry, ez + rz), player, serverLevel);
            }
        }
    }

    public void conductorParticle(float damage, float radius, Vec3 placement, Player source, Level level){
        if(level instanceof ServerLevel serverLevel){
            serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, placement.x, placement.y, placement.z, 15, radius, radius, radius, 0.05);
        }
        for (LivingEntity entity : EntityUtils.entityCollector(placement, radius, level)){
            if(!entity.equals(source)){
                entity.hurt(ModDamageSource.cryomancy(source), damage);
                ItemUtils.changeBlaze(source, this.blazeRegained);
            }
        }
        if(source instanceof ServerPlayer sp) sp.sendSystemMessage(Component.literal("balls"));
    }
}
