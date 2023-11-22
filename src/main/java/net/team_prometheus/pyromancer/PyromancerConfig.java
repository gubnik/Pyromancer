package net.team_prometheus.pyromancer;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.IConfigEvent;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class PyromancerConfig implements IConfigEvent {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final PyromancerConfig COMMON;
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final PyromancerConfig CLIENT;
    static {
        final Pair<PyromancerConfig, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(PyromancerConfig::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();
        final Pair<PyromancerConfig, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(PyromancerConfig::new);
        CLIENT_SPEC = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
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
