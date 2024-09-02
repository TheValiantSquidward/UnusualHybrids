package net.thevaliantsquidward.unusualhybrids.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thevaliantsquidward.unusualhybrids.UnusualHybrids;
import net.thevaliantsquidward.unusualhybrids.entity.ModEntities;
import net.thevaliantsquidward.unusualhybrids.item.custom.FusionScytheItem;
import net.thevaliantsquidward.unusualhybrids.item.tier.ModItemTiers;
import net.thevaliantsquidward.unusualhybrids.sound.ModSounds;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, UnusualHybrids.MOD_ID);

    public static final RegistryObject<Item> CARBON_SLUDGE = ITEMS.register("carbon_sludge", () -> new Item(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE)));

    public static final RegistryObject<Item> INDOM_FLASK = ITEMS.register("indominus_rex_dna_flask", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MAJUNGAR_FLASK = ITEMS.register("majungar_flask", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> INDOM_SCALES = ITEMS.register("indominus_scales", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MAJUNGAR_TALON = ITEMS.register("majungar_talon", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> INDOM_SPAWN_EGG = ITEMS.register("indominus_rex_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.INDOM, 0xFCFBFB, 0xDDDBDB, new Item.Properties()));
    public static final RegistryObject<Item> MAJUNGAR_SPAWN_EGG = ITEMS.register("majungaraptor_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.MAJUNGARAPTOR, 0x635d48, 0x7c7f16, new Item.Properties()));

    public static final RegistryObject<Item> TALON = ITEMS.register("talon",
            () -> new RecordItem(10, ModSounds.TALON, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 2060));

    public static final RegistryObject<Item> INDOMITABLE = ITEMS.register("indomitable",
            () -> new RecordItem(4, ModSounds.INDOMITABLE, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 1920));


    public static final RegistryObject<Item> FUSION_SCYTHE = ITEMS.register("fusion_scythe", () -> new FusionScytheItem(ModItemTiers.MAJUNGAR, 6, -2.1F));

    public static ItemStack checkEachHandForScythe(LivingEntity entity) {
        InteractionHand hand = entity.getMainHandItem().getItem() instanceof FusionScytheItem ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        return entity.getItemInHand(hand);
    }
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}