package net.team_prometheus.pyromancer.blocks.block_entities;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.team_prometheus.pyromancer.PyromancerMod;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, PyromancerMod.MOD_ID);
    public static void register(IEventBus bus){
        BLOCK_ENTITY.register(bus);
    }
}

