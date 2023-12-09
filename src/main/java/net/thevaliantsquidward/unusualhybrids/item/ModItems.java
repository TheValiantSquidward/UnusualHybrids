package net.thevaliantsquidward.unusualhybrids.item;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thevaliantsquidward.unusualhybrids.UnusualHybrids;
import net.thevaliantsquidward.unusualhybrids.entity.ModEntities;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, UnusualHybrids.MOD_ID);

    public static final RegistryObject<Item> CARBON_SLUDGE = ITEMS.register("carbon_sludge", () -> new Item(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE)));

    public static final RegistryObject<Item> INDOM_FLASK = ITEMS.register("indominus_rex_dna_flask", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> INDOM_SCALES = ITEMS.register("indominus_scales", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> INDOM_SPAWN_EGG = ITEMS.register("indominus_rex_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.INDOM, 0xFCFBFB, 0xDDDBDB, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}