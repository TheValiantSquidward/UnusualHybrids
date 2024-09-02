package net.thevaliantsquidward.unusualhybrids.recipe;

import net.thevaliantsquidward.unusualhybrids.UnusualHybrids;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class HybridizerRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;
    private final int maxProgress; // New field for progress time

    public HybridizerRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputItems, int maxProgress) {
        this.id = id;
        this.output = output;
        this.inputItems = NonNullList.withSize(2, Ingredient.EMPTY);
        for (int i = 0; i < inputItems.size(); i++) {
            this.inputItems.set(i, inputItems.get(i));
        }
        this.maxProgress = maxProgress; // Initialize with constructor
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        // Get the items from the container
        ItemStack slot1 = pContainer.getItem(1);
        ItemStack slot2 = pContainer.getItem(2);

        // Test both possible combinations
        boolean firstCombination = inputItems.get(0).test(slot1) && inputItems.get(1).test(slot2);
        boolean secondCombination = inputItems.get(0).test(slot2) && inputItems.get(1).test(slot1);

        // Return true if either combination is valid
        return firstCombination || secondCombination;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<HybridizerRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "hybridizing";
    }

    public static class Serializer implements RecipeSerializer<HybridizerRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(UnusualHybrids.MOD_ID, "hybridizing");

        @Override
        public HybridizerRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            int maxProgress = GsonHelper.getAsInt(json, "maxProgress", 200); // Default to 200 if not provided

            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new HybridizerRecipe(id, output, inputs, maxProgress);
        }

        @Override
        public HybridizerRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();
            int maxProgress = buf.readInt(); // Read maxProgress from buffer

            return new HybridizerRecipe(id, output, inputs, maxProgress);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, HybridizerRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.output, false);
            buf.writeInt(recipe.getMaxProgress());
        }
    }
}
