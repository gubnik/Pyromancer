package net.team_prometheus.pyromancer.damage_source;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class EntityPyromancyDamageSource extends PyromancyDamageSource {
    protected final Entity entity;
    private boolean isEmber;
    public EntityPyromancyDamageSource(String name, Entity entity) {
        super(name);
        this.entity = entity;
    }
    public boolean isEmber() {
        return this.isEmber;
    }
    public EntityPyromancyDamageSource setIsEmber(){
        this.isEmber = true;
        return this;
    }
    public Entity getEntity() {
        return this.entity;
    }
    public @NotNull Component getLocalizedDeathMessage(@NotNull LivingEntity livingEntity) {
        ItemStack itemstack = this.entity instanceof LivingEntity ? ((LivingEntity)this.entity).getMainHandItem() : ItemStack.EMPTY;
        String s = "death.attack." + this.msgId;
        return !itemstack.isEmpty() && itemstack.hasCustomHoverName() ? Component.translatable(s + ".item", livingEntity.getDisplayName(), this.entity.getDisplayName(), itemstack.getDisplayName()) : Component.translatable(s, livingEntity.getDisplayName(), this.entity.getDisplayName());
    }
    public boolean scalesWithDifficulty() {
        return this.entity instanceof LivingEntity && !(this.entity instanceof Player);
    }
    @Nullable
    public Vec3 getSourcePosition() {
        return this.entity.position();
    }

    public @NotNull String toString() {
        return "EntityDamageSource (" + this.entity + ")";
    }
}
