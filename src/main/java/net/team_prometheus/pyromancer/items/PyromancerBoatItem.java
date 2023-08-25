package net.team_prometheus.pyromancer.items;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.team_prometheus.pyromancer.entity.PyromancerBoatEntity;
import net.team_prometheus.pyromancer.entity.PyromancerChestBoatEntity;

import java.util.List;
import java.util.function.Predicate;

public class PyromancerBoatItem extends Item {
    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    private final PyromancerBoatEntity.ModelType type;
    private final boolean hasChest;

    public PyromancerBoatItem(boolean hasChest, PyromancerBoatEntity.ModelType type, Item.Properties properties)
    {
        super(properties);
        this.type = type;
        this.hasChest = hasChest;
        DispenserBlock.registerBehavior(this, new BoatDispenseItemBehaviour(hasChest, type));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack itemstack = player.getItemInHand(hand);
        HitResult hitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
        if (hitresult.getType() == HitResult.Type.MISS)
        {
            return InteractionResultHolder.pass(itemstack);
        }
        else
        {
            Vec3 vec3 = player.getViewVector(1.0F);
            double d0 = 5.0D;
            List<Entity> list = level.getEntities(player, player.getBoundingBox().expandTowards(vec3.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
            if (!list.isEmpty())
            {
                Vec3 vec31 = player.getEyePosition();

                for (Entity entity : list)
                {
                    AABB aabb = entity.getBoundingBox().inflate((double) entity.getPickRadius());
                    if (aabb.contains(vec31))
                    {
                        return InteractionResultHolder.pass(itemstack);
                    }
                }
            }

            if (hitresult.getType() == HitResult.Type.BLOCK)
            {
                Boat boat;

                if (this.hasChest)
                {
                    boat = new PyromancerChestBoatEntity(level, hitresult.getLocation().x, hitresult.getLocation().y, hitresult.getLocation().z);
                    ((PyromancerChestBoatEntity)boat).setModel(this.type);
                }
                else
                {
                    boat = new PyromancerBoatEntity(level, hitresult.getLocation().x, hitresult.getLocation().y, hitresult.getLocation().z);
                    ((PyromancerBoatEntity)boat).setModel(this.type);
                }

                boat.setYRot(player.getYRot());

                if (!level.noCollision(boat, boat.getBoundingBox()))
                {
                    return InteractionResultHolder.fail(itemstack);
                }
                else
                {
                    if (!level.isClientSide)
                    {
                        level.addFreshEntity(boat);
                        level.gameEvent(player, GameEvent.ENTITY_PLACE, hitresult.getLocation());
                        if (!player.getAbilities().instabuild)
                        {
                            itemstack.shrink(1);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
                }
            }
            else
            {
                return InteractionResultHolder.pass(itemstack);
            }
        }
    }
}
