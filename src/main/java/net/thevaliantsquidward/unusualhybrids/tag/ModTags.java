package net.thevaliantsquidward.unusualhybrids.tag;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.thevaliantsquidward.unusualhybrids.UnusualHybrids;


public class ModTags {

    public static final TagKey<Block> INDOM_BREAKABLES = registerBlockTag("indom_breakables");
    public static final TagKey<Block> MAJUNGAR_BREAKABLES = registerBlockTag("majungar_breakables");

    public static final TagKey<EntityType<?>> MAJUNGAR_TARGETS = registerEntityTag("majungar_targets");

    private static TagKey<Block> registerBlockTag(String name) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation(UnusualHybrids.MOD_ID, name));
    }
    private static TagKey<EntityType<?>> registerEntityTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(UnusualHybrids.MOD_ID, name));
    }
}
