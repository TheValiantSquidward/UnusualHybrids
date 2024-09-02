package net.thevaliantsquidward.unusualhybrids.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thevaliantsquidward.unusualhybrids.UnusualHybrids;
import net.thevaliantsquidward.unusualhybrids.block.ModBlocks;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, UnusualHybrids.MOD_ID);

    public static final RegistryObject<BlockEntityType<HybridizerBlockEntity>> HYBRIDIZER_BE =
            BLOCK_ENTITIES.register("hybridizer_be", () ->
                    BlockEntityType.Builder.of(HybridizerBlockEntity::new,
                            ModBlocks.HYBRIDIZER.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}