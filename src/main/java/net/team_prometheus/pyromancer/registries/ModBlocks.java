package net.team_prometheus.pyromancer.registries;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.blocks.FirebriarBlock;
import net.team_prometheus.pyromancer.blocks.SizzlingVineBlock;
import net.team_prometheus.pyromancer.blocks.WeirdSaplingBlock;
import net.team_prometheus.pyromancer.util.BlocksUtils;
import net.team_prometheus.pyromancer.worldgen.trees.tree_growers.PyrowoodTreeGrower;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class ModBlocks {
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PyromancerMod.MOD_ID);
    //pyrowood
    public static final RegistryObject<Block> PYROWOOD_LOG = registerBlock("pyrowood_log",
            () -> log(MaterialColor.WOOD, MaterialColor.PODZOL), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> STRIPPED_PYROWOOD_LOG = registerBlock("stripped_pyrowood_log",
            () -> log(MaterialColor.WOOD, MaterialColor.PODZOL), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> PYROWOOD_PLANKS = registerBlock("pyrowood_planks",
            () -> new Block((BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD))), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> PYROWOOD_STAIRS = registerBlock("pyrowood_stairs",
            () -> new StairBlock(PYROWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(PYROWOOD_PLANKS.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> PYROWOOD_SLAB = registerBlock("pyrowood_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(PYROWOOD_PLANKS.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> PYROWOOD_FENCE = registerBlock("pyrowood_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(PYROWOOD_PLANKS.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> PYROWOOD_FENCE_GATE = registerBlock("pyrowood_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(PYROWOOD_PLANKS.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> PYROWOOD_DOOR = registerBlock("pyrowood_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(PYROWOOD_PLANKS.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> PYROWOOD_TRAPDOOR = registerBlock("pyrowood_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(PYROWOOD_PLANKS.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> PYROWOOD_LEAVES = registerBlock("pyrowood_leaves",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.JUNGLE_LEAVES).lightLevel(s -> 15).emissiveRendering(Blocks::always)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> PYROWOOD_SAPLING = registerBlock("pyrowood_sapling",
            () -> new WeirdSaplingBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).color(MaterialColor.COLOR_ORANGE), new PyrowoodTreeGrower()), CreativeModeTab.TAB_DECORATIONS);
    //
    // flaming grove
    public static final RegistryObject<Block> PYROMOSSED_NETHERRACK = registerBlock("pyromossed_netherrack",
            () -> new Block(BlockBehaviour.Properties.of(Material.MOSS, MaterialColor.TERRACOTTA_ORANGE).strength(2.0F, 3.0F).sound(SoundType.NYLIUM)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> PYROMOSS_SPROUTS = registerBlock("pyromoss_sprouts",
            () -> new TallGrassBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)){
        protected boolean mayPlaceOn(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
            return BlocksUtils.flamingGrovePlantable(blockState);
        }
            }, CreativeModeTab.TAB_DECORATIONS);

    public static final RegistryObject<Block> SIZZLING_VINE = registerBlock("sizzling_vine",
            () -> new SizzlingVineBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.CAVE_VINES).emissiveRendering(Blocks::always)),
            CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> FIREBRIAR = registerBlock("firebriar",
            () -> new FirebriarBlock(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_ORANGE).strength(0,0).sound(SoundType.HARD_CROP).noCollission()), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> BLAZING_POPPY = registerBlock("blazing_poppy",
            () -> new FlowerBlock(MobEffects.HARM, 1, BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_ORANGE).strength(0,0).sound(SoundType.HARD_CROP).noCollission()){
                @Override
                protected boolean mayPlaceOn(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
                    return BlocksUtils.flamingGrovePlantable(blockState);
                }
            }, CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> NETHER_LILY = registerBlock("nether_lily",
            () -> new FlowerBlock(MobEffects.HARM, 1, BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_ORANGE).strength(0,0).sound(SoundType.HARD_CROP).noCollission().lightLevel(i -> 7).emissiveRendering(Blocks::always)){
                @Override
                protected boolean mayPlaceOn(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
                    return BlocksUtils.flamingGrovePlantable(blockState);
                }
            }, CreativeModeTab.TAB_DECORATIONS);
    //
    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> output = BLOCKS.register(name, block);
        registerBlockItem(name, output, tab);
        return output;
    }
    public static <T extends Block> void registerBlockItem(String name, Supplier<T> block, CreativeModeTab tab){
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }
    public static void register(IEventBus bus){
        BLOCKS.register(bus);
    }
    public static RotatedPillarBlock log(MaterialColor materialColor, MaterialColor materialColor1) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.NETHER_WOOD, (p_152624_) ->
                p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? materialColor : materialColor1).strength(2.0F).sound(SoundType.WOOD));
    }
}
