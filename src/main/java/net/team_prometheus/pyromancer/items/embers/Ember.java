package net.team_prometheus.pyromancer.items.embers;

import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TieredItem;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.items.MaceItem;

import java.security.InvalidParameterException;
import java.util.Objects;
import java.util.function.Function;

public enum Ember {

    // FLAME INFUSION

    ASHEN_FORM("ashen_form", 1, 8d, EmberAttacks::ashenForm, EmberInfusionType.FLAME, EmberWeaponType.ANY,
            Objects.requireNonNull(PlayerAnimationRegistry.getAnimation(new ResourceLocation(PyromancerMod.MOD_ID, "ashen_form"))), 0, 5, 15),
    AEGIS_OF_FIRE("aegis_of_fire", 2, 10d, EmberAttacks::aegisOfFire, EmberInfusionType.FLAME, EmberWeaponType.MACE,
            Objects.requireNonNull(PlayerAnimationRegistry.getAnimation(new ResourceLocation(PyromancerMod.MOD_ID, "aegis_of_fire"))), 0, 10, 30),

    // SOULFLAME INFUSION

    SOULFLAME_IGNITION("soulflame_ignition", 8, 5d, EmberAttacks::soulflameIgnition, EmberInfusionType.SOULFLAME, EmberWeaponType.ANY,
            Objects.requireNonNull(PlayerAnimationRegistry.getAnimation(new ResourceLocation(PyromancerMod.MOD_ID, "soulflame_ignition"))), 4, 11, 40),
    TORNADO_OF_SOULS("tornado_of_souls", 16, 4d, EmberAttacks::tornadoOfSouls, EmberInfusionType.SOULFLAME, EmberWeaponType.AXE,
            Objects.requireNonNull(PlayerAnimationRegistry.getAnimation(new ResourceLocation(PyromancerMod.MOD_ID, "tornado_of_souls"))), 5, 8, 100),

    // HELLBLAZE INFUSION

    HEAVENLY_FLAME("heavenly_flame", 8, 4d, EmberAttacks::heavenlyFlame, EmberInfusionType.HELLBLAZE, EmberWeaponType.SWORD,
            Objects.requireNonNull(PlayerAnimationRegistry.getAnimation(new ResourceLocation(PyromancerMod.MOD_ID, "heavenly_flame"))), 10, 60, 100);

    private final String name;
    private final int cost;
    private final double damage;
    private final Function<Player, Integer> function;
    private final EmberInfusionType infusionType;
    private final EmberWeaponType weaponType;
    private final KeyframeAnimation animation;
    private final int animationDelay;
    private final int animationDuration;
    private final int cooldown;
    Ember(String name, int cost, double damage, Function<Player, Integer> function, EmberInfusionType infusionType, EmberWeaponType weaponType, KeyframeAnimation animation, int delay, int animationDuration, int cooldown){
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
    }
    public String getName(){return this.name;}
    public int getCost(){return this.cost;}
    public double getDamage(){return this.damage;}
    public Function<Player, Integer> getFunction(){return this.function;}
    public EmberInfusionType getInfusionType(){return this.infusionType;}
    public EmberWeaponType getWeaponType(){return this.weaponType;}
    public KeyframeAnimation getAnimation(){return this.animation;}
    public int getAnimationDelay(){return this.animationDelay;}
    public int getAnimationDuration(){return this.animationDuration;}
    public int getCooldown(){return this.cooldown;}
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
}
