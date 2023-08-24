package net.team_prometheus.pyromancer.init;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.IConfigEvent;
import net.minecraftforge.fml.config.ModConfig;

public class PyromancerConfig implements IConfigEvent {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final PyromancerConfig COMMON;
    static {
        COMMON_SPEC = new ForgeConfigSpec.Builder().configure(PyromancerConfig::new).getRight();
        COMMON = new ForgeConfigSpec.Builder().configure(PyromancerConfig::new).getLeft();
    }
    public final ForgeConfigSpec.ConfigValue<Integer> journalMaxCapacity;
    public final ForgeConfigSpec.ConfigValue<Integer> blazeValue;
    PyromancerConfig(ForgeConfigSpec.Builder builder){
        builder.push("Blazing Journal Configuration");
        journalMaxCapacity = builder
                .comment("Max Blaze value a Blazing Journal can hold: ")
                .defineInRange("Capacity: ", 512, 64, 8192);
        blazeValue = builder
                .comment("Value that one blaze powder provides: ")
                .defineInRange("Value: ", 8, 1, 8192);
        // builder.pop();
    }
    @Override
    public ModConfig getConfig() {
        return null;
    }
}
