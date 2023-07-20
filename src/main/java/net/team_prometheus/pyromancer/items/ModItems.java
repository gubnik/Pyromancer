package net.team_prometheus.pyromancer.items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.entity.ModEntities;
import net.team_prometheus.pyromancer.init.ModTabs;
import net.team_prometheus.pyromancer.items.throwables.BombsackItem;
import net.team_prometheus.pyromancer.items.throwables.NapalmBombsackItem;
@SuppressWarnings("unused")
public class ModItems {
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PyromancerMod.MOD_ID);
    // maces
    public static RegistryObject<Item> WOODEN_MACE = ITEMS.register("wooden_mace",
            () -> new MaceItem(Tiers.WOOD, 5, -2.8f, 0.5f, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)));
    public static RegistryObject<Item> STONE_MACE = ITEMS.register("stone_mace",
            () -> new MaceItem(Tiers.STONE, 5, -2.8f, 0.5f, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)));
    public static RegistryObject<Item> GOLDEN_MACE = ITEMS.register("golden_mace",
            () -> new MaceItem(Tiers.GOLD, 5, -2.8f, 0.5f, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)));
    public static RegistryObject<Item> IRON_MACE = ITEMS.register("iron_mace",
            () -> new MaceItem(Tiers.IRON, 5, -2.8f, 0.5f, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)));
    public static RegistryObject<Item> DIAMOND_MACE = ITEMS.register("diamond_mace",
            () -> new MaceItem(Tiers.DIAMOND, 5, -2.8f, 0.5f, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)));
    public static RegistryObject<Item> NETHERITE_MACE = ITEMS.register("netherite_mace",
            () -> new MaceItem(Tiers.NETHERITE, 5, -2.8f, 0.5f, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)));
    // throwable
    public static RegistryObject<Item> BOMBSACK = ITEMS.register("bombsack",
            () -> new BombsackItem(new Item.Properties().tab(ModTabs.PYROMANCER_TAB).stacksTo(16)));
    public static RegistryObject<Item> NAPALM_BOMBSACK = ITEMS.register("napalm_bombsack",
            () -> new NapalmBombsackItem(new Item.Properties().tab(ModTabs.PYROMANCER_TAB).stacksTo(16)));
    // journal
    public static RegistryObject<Item> BLAZING_JOURNAL = ITEMS.register("blazing_journal",
            () -> new BlazingJournal(new Item.Properties().tab(ModTabs.PYROMANCER_TAB)));
    // quills
    public static RegistryObject<Item> BLAZING_QUILL = ITEMS.register("blazing_quill",
            () -> new QuillItem(new Item.Properties().tab(ModTabs.PYROMANCER_TAB)));
    public static RegistryObject<Item> SMOLDERING_TWIG = ITEMS.register("smoldering_twig",
            () -> new QuillItem(new Item.Properties().tab(ModTabs.PYROMANCER_TAB)));
    // pyromancies
    public static RegistryObject<Item> SIZZLING_HAND = ITEMS.register("sizzling_hand",
            () -> new SizzlingHand(new Item.Properties().tab(ModTabs.PYROMANCER_TAB).stacksTo(1)));
    public static RegistryObject<Item> EMBER = ITEMS.register("ember",
            () -> new EmberItem(new Item.Properties().tab(ModTabs.PYROMANCER_TAB).stacksTo(1)));
    // spawn eggs
    public static RegistryObject<Item> UNBURNED_SPAWN_EGG = ITEMS.register("unburned_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.UNBURNED, -10268354, -3297142, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static void register (IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
