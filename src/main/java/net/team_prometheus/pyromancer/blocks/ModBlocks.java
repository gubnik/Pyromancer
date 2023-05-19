package net.team_prometheus.pyromancer.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.items.ModItems;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class ModBlocks {
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PyromancerMod.MOD_ID);
    //pyrowood
    public static final RegistryObject<Block> PYROWOOD_LOG = registerBlock("pyrowood_log",
            () -> log(MaterialColor.WOOD, MaterialColor.PODZOL), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> PYROWOOD_PLANKS = registerBlock("pyrowood_planks",
            () -> new Block((BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD))), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> PYROWOOD_STAIRS = registerBlock("pyrowood_stairs",
            () -> new StairBlock(PYROWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(PYROWOOD_PLANKS.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> PYROWOOD_SLAB = registerBlock("pyrowood_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(PYROWOOD_PLANKS.get())), CreativeModeTab.TAB_BUILDING_BLOCKS);
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
