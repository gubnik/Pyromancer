package net.team_prometheus.pyromancer.items.weaponary.pyromancy;

import net.minecraft.world.item.Item;

public class PyromancyItem extends Item {
    public final int blazeConsumptionModifier;
    public final int pyromancyDamageModifier;
    public int blazeConsumption = 0;
    public PyromancyItem(Properties properties, int blazeConsumptionModifier, int pyromancyDamageModifier) {
        super(properties);
        this.blazeConsumptionModifier = blazeConsumptionModifier;
        this.pyromancyDamageModifier = pyromancyDamageModifier;
    }
}
