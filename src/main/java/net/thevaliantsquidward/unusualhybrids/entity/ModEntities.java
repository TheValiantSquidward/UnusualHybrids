package net.thevaliantsquidward.unusualhybrids.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thevaliantsquidward.unusualhybrids.UnusualHybrids;
import net.thevaliantsquidward.unusualhybrids.entity.custom.IndominousRexEntity;
import net.thevaliantsquidward.unusualhybrids.entity.custom.MajungaraptorEntity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, UnusualHybrids.MOD_ID);


    public static final RegistryObject<EntityType<IndominousRexEntity>> INDOM =
            ENTITY_TYPES.register("indominus_rex",
                    () -> EntityType.Builder.of(IndominousRexEntity::new, MobCategory.CREATURE)
                            .sized(4.0F, 5.3F)
                            .build(new ResourceLocation(UnusualHybrids.MOD_ID, "indominus_rex").toString()));

    public static final RegistryObject<EntityType<MajungaraptorEntity>> MAJUNGARAPTOR = ENTITY_TYPES.register("majungaraptor",
            () -> EntityType.Builder.of(MajungaraptorEntity::new, MobCategory.CREATURE).sized(1.95F, 2.2F)
                    .build(new ResourceLocation(UnusualHybrids.MOD_ID, "majungaraptor").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}