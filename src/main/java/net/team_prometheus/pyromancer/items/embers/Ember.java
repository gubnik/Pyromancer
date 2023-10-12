package net.team_prometheus.pyromancer.items.embers;

import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TieredItem;
import net.team_prometheus.pyromancer.animations.AnimationList;
import net.team_prometheus.pyromancer.items.MaceItem;
import java.security.InvalidParameterException;
import java.util.function.Consumer;

public enum Ember {

    // FLAME INFUSION

    ASHEN_FORM("ashen_form", 1, 8f, EmberAttacks::ashenForm, EmberInfusionType.FLAME, EmberWeaponType.ANY,
            AnimationList.ASHEN_FORM(), 0, 5, 15,
            true, EndEvent.SIMPLE_STOP, false, 0, EmberAttacks::empty),
    AEGIS_OF_FIRE("aegis_of_fire", 2, 10f, EmberAttacks::aegisOfFire, EmberInfusionType.FLAME, EmberWeaponType.MACE,
            AnimationList.AEGIS_OF_FIRE(), 0, 10, 30,
            true, EndEvent.SIMPLE_STOP, false, 0, EmberAttacks::empty),

    // SOULFLAME INFUSION

    SOULFLAME_IGNITION("soulflame_ignition", 8, 5f, EmberAttacks::soulflameIgnition, EmberInfusionType.SOULFLAME, EmberWeaponType.ANY,
            AnimationList.SOULFLAME_IGNITION(), 4, 11, 40,
            false, EndEvent.SIMPLE_STOP, false, 0, EmberAttacks::empty),
    TORNADO_OF_SOULS("tornado_of_souls", 16, 4f, EmberAttacks::tornadoOfSouls, EmberInfusionType.SOULFLAME, EmberWeaponType.AXE,
            AnimationList.TORNADO_OF_SOULS(), 3, 10, 100,
            false, EndEvent.SIMPLE_STOP, false, 0, EmberAttacks::empty),

    // HELLBLAZE INFUSION

    HEAVENLY_FLAME("heavenly_flame", 1, 4f, EmberAttacks::heavenlyFlame, EmberInfusionType.HELLBLAZE, EmberWeaponType.SWORD,
            AnimationList.HEAVENLY_FLAME(), 10, 60, 100,
            true, EndEvent.SIMPLE_STOP, true, 20, EmberAttacks::heavenlyFlame);

    private final String name;
    private final int cost;
    private final float damage;
    private final Consumer<Player> function;
    private final EmberInfusionType infusionType;
    private final EmberWeaponType weaponType;
    private final KeyframeAnimation animation;
    private final int animationDelay;
    private final int animationDuration;
    private final int cooldown;
    private final boolean held;
    private final EndEvent endEvent;
    private final boolean activatedOnInterval;
    private final int interval;
    private final Consumer<Player> intervalUsageEvent;

    Ember(String name, int cost, float damage, Consumer<Player> function, EmberInfusionType infusionType, EmberWeaponType weaponType,
          KeyframeAnimation animation, int delay, int animationDuration,
          int cooldown, boolean held, EndEvent endEvent, boolean activatedOnInterval, int interval, Consumer<Player> intervalUsageEvent){
        this.name = name;
        this.cost = cost;
        this.damage = damage;
        this.function = function;
        this.infusionType = infusionType;
        this.weaponType = weaponType;
        this.animation = animation;
        this.animationDelay = delay;
        this.animationDuration = animationDuration;
        this.cooldown = cooldown;
        this.held = held;
        this.endEvent = endEvent;
        this.activatedOnInterval = activatedOnInterval;
        this.interval = interval;
        this.intervalUsageEvent = intervalUsageEvent;
    }

    // GETTERS
    public String getName(){return this.name;}
    public int getCost(){return this.cost;}
    public float getDamage(){return this.damage;}
    public Consumer<Player> getFunction(){return this.function;}
    public EmberInfusionType getInfusionType(){return this.infusionType;}
    public EmberWeaponType getWeaponType(){return this.weaponType;}
    public KeyframeAnimation getAnimation(){return this.animation;}
    public int getAnimationDelay(){return this.animationDelay;}
    public int getAnimationDuration(){return this.animationDuration;}
    public int getCooldown(){return this.cooldown;}
    public boolean getHeld(){return this.held;}
    public EndEvent getEndEvent(){return this.endEvent;}
    public boolean getActivatedOnInterval(){return this.activatedOnInterval;}
    public int getInterval(){return this.interval;}
    public Consumer<Player> getIntervalUsageEvent(){return this.intervalUsageEvent;}


    public static Ember byName(String name){
        for(Ember ember : values()){
            if(ember.getName().equals(name)){
                return ember;
            }
        }
        throw new InvalidParameterException("Invalid Ember tag:");
    }
    public static boolean isValidEmber(String name){
        boolean flag = false;
        for(Ember ember : values()){
            if(ember.getName().equals(name)){
                flag = true;
                break;
            }
        }
        return flag;
    }
    public enum EmberInfusionType{
        FLAME(ChatFormatting.YELLOW),
        SOULFLAME(ChatFormatting.AQUA),
        HELLBLAZE(ChatFormatting.RED);
        private final ChatFormatting color;
        EmberInfusionType(ChatFormatting color){
            this.color = color;
        }
        public ChatFormatting getColor(){return this.color;}
    }
    public enum EmberWeaponType{
        ANY(TieredItem.class),
        SWORD(SwordItem.class),
        AXE(AxeItem.class),
        MACE(MaceItem.class);
        private final Class<? extends TieredItem> weapon;
        <T extends TieredItem> EmberWeaponType(Class<T> tClass){
            this.weapon = tClass;
        }
        public Class<? extends TieredItem> getWeapon(){return this.weapon;}
    }
    public enum EndEvent{
        SIMPLE_STOP(EmberUtilities::stopAnimation);
        private final Consumer<Player> playerConsumer;
        EndEvent(Consumer<Player> playerConsumer){
            this.playerConsumer = playerConsumer;
        }
        public Consumer<Player> getPlayerConsumer(){return this.playerConsumer;}
    }
}
