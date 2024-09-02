package net.thevaliantsquidward.unusualhybrids.block.entity;

import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.thevaliantsquidward.unusualhybrids.item.ModItems;
import net.thevaliantsquidward.unusualhybrids.recipe.HybridizerRecipe;
import net.thevaliantsquidward.unusualhybrids.screen.HybridizerMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class HybridizerBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemhandler = new ItemStackHandler(4);
    private final int INPUT_FUEL_SLOT = 0;
    private final int INPUT_DNA_1_SLOT = 1;
    private final int INPUT_DNA_2_SLOT = 2;
    private final int OUTPUT_DNA_SLOT = 3;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 2000; // Default value, overridden by recipe-specific value
    private int fuel = 0;
    private int maxFuel = 640;

    public HybridizerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.HYBRIDIZER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> HybridizerBlockEntity.this.progress;
                    case 1 -> HybridizerBlockEntity.this.maxProgress;
                    case 2 -> HybridizerBlockEntity.this.fuel;
                    case 3 -> HybridizerBlockEntity.this.maxFuel;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> HybridizerBlockEntity.this.progress = pValue;
                    case 1 -> HybridizerBlockEntity.this.maxProgress = pValue;
                    case 2 -> HybridizerBlockEntity.this.fuel = pValue;
                    case 3 -> HybridizerBlockEntity.this.maxFuel = pValue;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemhandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemhandler.getSlots());
        for (int i = 0; i < itemhandler.getSlots(); i++) {
            inventory.setItem(i, itemhandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.unusualhybrids.hybridizer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new HybridizerMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemhandler.serializeNBT());
        pTag.putInt("hybridizer.progress", progress);
        pTag.putInt("hybridizer.fuel", fuel);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemhandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("hybridizer.progress");
        fuel = pTag.getInt("hybridizer.fuel");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        Optional<HybridizerRecipe> currentRecipe = getCurrentRecipe();

        if (currentRecipe.isPresent()) {
            maxProgress = currentRecipe.get().getMaxProgress(); // Set maxProgress from the recipe

            if (hasRecipe() && hasFuel()) {
                increaseCraftingProgress();
                decreaseFuel();
                setChanged(pLevel, pPos, pState);
                if (hasProgressFinished()) {
                    craftItem();
                    resetProgress();
                }
            } else {
                refuel();
                setChanged(pLevel, pPos, pState);
            }
        } else {
            resetProgress(); // Reset progress if no valid recipe
        }
    }

    private void refuel() {
        ItemStack fuelStack = itemhandler.getStackInSlot(INPUT_FUEL_SLOT);
        int fuelAmount = getFuelAmount(fuelStack);
        if (!hasFuel() && fuelAmount > 0) {
            itemhandler.extractItem(INPUT_FUEL_SLOT, 1, false);
            fuel = maxFuel;
        } else {
            resetProgress();
        }
    }

    private int getFuelAmount(final ItemStack fuelStack) {
        if (!fuelStack.isEmpty() && fuelStack.is(ModItems.CARBON_SLUDGE.get())) {
            return maxFuel;
        }
        return 0;
    }

    private void decreaseFuel() {
        fuel--;
    }

    private boolean hasFuel() {
        return fuel > 0 && fuel <= maxFuel;
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        Optional<HybridizerRecipe> recipe = getCurrentRecipe();
        if (recipe.isPresent()) {
            ItemStack result = recipe.get().getResultItem(null);

            this.itemhandler.extractItem(INPUT_DNA_1_SLOT, 1, false);
            this.itemhandler.extractItem(INPUT_DNA_2_SLOT, 1, false);

            this.itemhandler.setStackInSlot(OUTPUT_DNA_SLOT, new ItemStack(result.getItem(),
                    this.itemhandler.getStackInSlot(OUTPUT_DNA_SLOT).getCount() + result.getCount()));
        }
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        Optional<HybridizerRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            return false;
        }
        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<HybridizerRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemhandler.getSlots());
        for (int i = 0; i < itemhandler.getSlots(); i++) {
            inventory.setItem(i, this.itemhandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(HybridizerRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemhandler.getStackInSlot(OUTPUT_DNA_SLOT).isEmpty() || this.itemhandler.getStackInSlot(OUTPUT_DNA_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemhandler.getStackInSlot(OUTPUT_DNA_SLOT).getCount() + count <= this.itemhandler.getStackInSlot(OUTPUT_DNA_SLOT).getMaxStackSize();
    }
}

