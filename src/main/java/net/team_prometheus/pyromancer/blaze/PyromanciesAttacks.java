package net.team_prometheus.pyromancer.blaze;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.team_prometheus.pyromancer.entity.ModEntities;
import net.team_prometheus.pyromancer.entity.projectiles.SizzlingHandFireball;
import net.team_prometheus.pyromancer.items.BlazingJournal;
import net.team_prometheus.pyromancer.items.PyromancyItem;

@Mod.EventBusSubscriber
public class PyromanciesAttacks {
    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event){
        if(event.getItemStack().getItem() instanceof PyromancyItem && event.getEntity().getOffhandItem().getItem() instanceof BlazingJournal){
            Level level = event.getLevel();
            Player entity = event.getEntity();
            ItemStack journal = event.getEntity().getOffhandItem();
            switch (event.getItemStack().getItem().toString()){
                case("sizzling_hand"):
                    sizzlingHandAttack(entity, level, journal);
                    break;
                default:
                    break;
            }
        }
    }
    public static void sizzlingHandAttack(Entity entity, Level level, ItemStack journal){
        if (!level.isClientSide) {
            SizzlingHandFireball fireball = new SizzlingHandFireball(ModEntities.SIZZLING_HAND_FIREBALL.get(), level);
            fireball.setOwner(entity);
            fireball.setPos(entity.getX(), entity.getEyeY() - 0.1, entity.getZ());
            fireball.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 1.5f, 0.1f);
            level.addFreshEntity(fireball);
        }
    }
}
