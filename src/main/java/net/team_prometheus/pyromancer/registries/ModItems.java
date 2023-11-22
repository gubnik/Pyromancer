package net.team_prometheus.pyromancer.registries;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.entity.PyromancerBoatEntity;
import net.team_prometheus.pyromancer.items.EmberItem;
import net.team_prometheus.pyromancer.items.PyromancerBoatItem;
import net.team_prometheus.pyromancer.items.armor.PyromancerArmorItem;
import net.team_prometheus.pyromancer.items.blazing_journal.BlazingJournalItem;
import net.team_prometheus.pyromancer.items.blazing_journal.QuillItem;
import net.team_prometheus.pyromancer.items.coating.CoatingItem;
import net.team_prometheus.pyromancer.items.compendium.CompendiumOfFlame;
import net.team_prometheus.pyromancer.items.weaponary.MaceItem;
import net.team_prometheus.pyromancer.items.weaponary.cryomancy.ConductorItem;
import net.team_prometheus.pyromancer.items.weaponary.pyromancy.CourtOfEmbersItem;
import net.team_prometheus.pyromancer.items.weaponary.pyromancy.SizzlingHandItem;
import net.team_prometheus.pyromancer.items.weaponary.throwables.BombsackItem;
import net.team_prometheus.pyromancer.items.weaponary.throwables.NapalmBombsackItem;

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
            () -> new BlazingJournalItem(new Item.Properties().tab(ModTabs.PYROMANCER_TAB), true));
    public static RegistryObject<Item> COMPENDIUM_OF_FLAME = ITEMS.register("compendium_of_flame",
            () -> new CompendiumOfFlame(new Item.Properties().tab(ModTabs.PYROMANCER_TAB), true));


    // quills
    public static RegistryObject<Item> BLAZING_QUILL = ITEMS.register("blazing_quill",
            () -> new QuillItem(new Item.Properties().tab(ModTabs.PYROMANCER_TAB), 0, 0));
    public static RegistryObject<Item> SMOLDERING_TWIG = ITEMS.register("smoldering_twig",
            () -> new QuillItem(new Item.Properties().tab(ModTabs.PYROMANCER_TAB), +1, +1));


    // pyromancies
    public static RegistryObject<Item> SIZZLING_HAND = ITEMS.register("sizzling_hand",
            () -> new SizzlingHandItem(new Item.Properties().tab(ModTabs.PYROMANCER_TAB).stacksTo(1)));
    public static RegistryObject<Item> COURT_OF_EMBERS = ITEMS.register("court_of_embers",
            () -> new CourtOfEmbersItem(new Item.Properties().tab(ModTabs.PYROMANCER_TAB).stacksTo(1)));
    public static RegistryObject<Item> EMBER = ITEMS.register("ember",
            () -> new EmberItem(new Item.Properties().tab(ModTabs.PYROMANCER_TAB).stacksTo(1)));

    // cryomancies
    public static RegistryObject<Item> CONDUCTOR = ITEMS.register("conductor",
            ConductorItem::new);


    // armor
    public static RegistryObject<Item> PYROMANCER_HELMET = ITEMS.register("pyromancer_helmet",
            () -> new PyromancerArmorItem(EquipmentSlot.HEAD));
    public static RegistryObject<Item> PYROMANCER_CHESTPLATE = ITEMS.register("pyromancer_chestplate",
            () -> new PyromancerArmorItem(EquipmentSlot.CHEST));
    public static RegistryObject<Item> PYROMANCER_LEGGINGS = ITEMS.register("pyromancer_leggings",
            () -> new PyromancerArmorItem(EquipmentSlot.LEGS));
    public static RegistryObject<Item> PYROMANCER_BOOTS = ITEMS.register("pyromancer_boots",
            () -> new PyromancerArmorItem(EquipmentSlot.FEET));
    //coatings
    public static RegistryObject<Item> MERCURY_COATING = ITEMS.register("mercury_coating",
            () -> new CoatingItem(new Item.Properties().stacksTo(1).tab(ModTabs.PYROMANCER_TAB), ModMobEffects.MERCURY_COATING.get()));


    // spawn eggs
    public static RegistryObject<Item> UNBURNED_SPAWN_EGG = ITEMS.register("unburned_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.UNBURNED, -10268354, -3297142, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));


    // boat
    public static RegistryObject<Item> PYROWOOD_BOAT = ITEMS.register("pyrowood_boat",
            () -> new PyromancerBoatItem(false, PyromancerBoatEntity.ModelType.PYROWOOD,
                    new Item.Properties().tab(ModTabs.PYROMANCER_TAB)));
    public static void register (IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
