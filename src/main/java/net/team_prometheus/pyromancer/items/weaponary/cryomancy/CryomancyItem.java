package net.team_prometheus.pyromancer.items.weaponary.cryomancy;

import net.minecraft.world.item.Item;

public class CryomancyItem extends Item {
    public final int blazeRegained;
    public final float damage;
    public CryomancyItem(Properties properties, int blazeRegained, float damage) {
        super(properties);
        this.blazeRegained = blazeRegained;
        this.damage = damage;
    }
    public void regainBlaze(double amount){
    }
}
