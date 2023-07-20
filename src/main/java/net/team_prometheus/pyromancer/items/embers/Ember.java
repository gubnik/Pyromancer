package net.team_prometheus.pyromancer.items.embers;

import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TieredItem;
import net.team_prometheus.pyromancer.items.MaceItem;

import java.util.Objects;
import java.util.function.Function;

public enum Ember {
    SOULFLAME_IGNITION("soulflame_ignition", 2, 5d, EmberAttacks::soulflameIgnition, EmberInfusionType.SOULFLAME, EmberWeaponType.SWORD,
            Objects.requireNonNull(PlayerAnimationRegistry.getAnimation(new ResourceLocation("pyromancer", "soulflame_ignition"))), 4, 100),
    ASHEN_FORM("ashen_form", 4, 8d, EmberAttacks::ashenForm, EmberInfusionType.FLAME, EmberWeaponType.ANY,
            Objects.requireNonNull(PlayerAnimationRegistry.getAnimation(new ResourceLocation("pyromancer", "ashen_form"))), 2, 100);
    private final String name;
    private final int cost;
    private final double damage;
    private final Function<Player, Integer> function;
    private final EmberInfusionType infusionType;
    private final EmberWeaponType weaponType;
    private final KeyframeAnimation animation;
    private final int animationDelay;
    private final int cooldown;
    Ember(String name, int cost, double damage, Function<Player, Integer> function, EmberInfusionType infusionType, EmberWeaponType weaponType, KeyframeAnimation animation, int delay, int cooldown){
        this.name = name;
        this.cost = cost;
        this.damage = damage;
        this.function = function;
        this.infusionType = infusionType;
        this.weaponType = weaponType;
        this.animation = animation;
        this.animationDelay = delay;
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
    public int getCooldown(){return this.cooldown;}
    public static Ember byName(String name){
        for(Ember ember : values()){
            if(ember.getName().equals(name)){
                return ember;
            }
        }
        throw new IllegalArgumentException("Invalid Ember '" + name + "'");
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