package net.team_prometheus.pyromancer.blocks.block_entities;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.team_prometheus.pyromancer.PyromancerMod;
import net.team_prometheus.pyromancer.blocks.ModBlocks;
import net.team_prometheus.pyromancer.pyromancer_table.PyromancerTableBlockEntity;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, PyromancerMod.MOD_ID);
    public static final RegistryObject<BlockEntityType<PyromancerTableBlockEntity>> PYROMANCER_TABLE = BLOCK_ENTITY.register("pyromancer_table",
            () -> BlockEntityType.Builder.of(PyromancerTableBlockEntity::new, ModBlocks.PYROMANCER_TABLE.get()).build(null));
    public static void register(IEventBus bus){
        BLOCK_ENTITY.register(bus);
    }
}

