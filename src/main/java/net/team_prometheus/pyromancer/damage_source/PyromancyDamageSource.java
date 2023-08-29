package net.team_prometheus.pyromancer.damage_source;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.team_prometheus.pyromancer.init.ModAttributes;
import net.team_prometheus.pyromancer.items.embers.Ember;
@Mod.EventBusSubscriber
public class PyromancyDamageSource extends DamageSource {
    protected Ember.EmberInfusionType type;
    public PyromancyDamageSource(String name) {
        super(name);
        this.setIsFire();
    }
    public PyromancyDamageSource setFlame(){
        this.type = Ember.EmberInfusionType.FLAME;
        return this;
    }
    public PyromancyDamageSource setSoulflame(){
        this.type = Ember.EmberInfusionType.SOULFLAME;
        return this;
    }
    public PyromancyDamageSource setHellblaze(){
        this.type = Ember.EmberInfusionType.HELLBLAZE;
        return this;
    }
    public boolean isFlame(){
        return this.type.equals(Ember.EmberInfusionType.FLAME);
    }
    public boolean isSoulflame(){
        return this.type.equals(Ember.EmberInfusionType.SOULFLAME);
    }
    public boolean isHellblaze(){
        return this.type.equals(Ember.EmberInfusionType.HELLBLAZE);
    }
    public Ember.EmberInfusionType getType(){return this.type;}

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void damageIncrease(LivingDamageEvent event){
        float increase = 0F;
        if(event.getSource() instanceof EntityPyromancyDamageSource damageSource
                && damageSource.getEntity() instanceof LivingEntity entity){
            switch (damageSource.getType()) {
                case FLAME -> increase += entity.getAttributeValue(ModAttributes.FLAME_DAMAGE.get());
                case SOULFLAME -> increase += entity.getAttributeValue(ModAttributes.SOULFLAME_DAMAGE.get());
                case HELLBLAZE -> increase += entity.getAttributeValue(ModAttributes.HELLBLAZE_DAMAGE.get());
            }
            if(!damageSource.isEmber()) increase += entity.getAttributeValue(ModAttributes.PYROMANCY_DAMAGE.get());

            event.setAmount(event.getAmount() + increase);
        }
    }
}
