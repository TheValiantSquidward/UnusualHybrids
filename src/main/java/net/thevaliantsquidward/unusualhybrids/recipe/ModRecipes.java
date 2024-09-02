package net.thevaliantsquidward.unusualhybrids.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thevaliantsquidward.unusualhybrids.UnusualHybrids;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, UnusualHybrids.MOD_ID);

    public static final RegistryObject<RecipeSerializer<HybridizerRecipe>> HYBRIDIZING_SERIALIZER =
            SERIALIZERS.register("hybridizing", () -> HybridizerRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
