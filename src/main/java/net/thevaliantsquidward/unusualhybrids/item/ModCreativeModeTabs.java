package net.thevaliantsquidward.unusualhybrids.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.thevaliantsquidward.unusualhybrids.UnusualHybrids;
import net.thevaliantsquidward.unusualhybrids.block.ModBlocks;


public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, UnusualHybrids.MOD_ID);

    public static final RegistryObject<CreativeModeTab> UNUSUAL_HYBRID_TAB = CREATIVE_MODE_TABS.register("hybrid_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CARBON_SLUDGE.get()))
                    .title(Component.translatable("creativetab.hybrid_tab"))
                    .displayItems((pParameters, pOutput) -> {


                        pOutput.accept(ModItems.CARBON_SLUDGE.get());


                        pOutput.accept(ModItems.INDOM_SCALES.get());

                        pOutput.accept(ModItems.TALON.get());
                        pOutput.accept(ModItems.INDOMITABLE.get());
                        pOutput.accept(ModItems.FUSION_SCYTHE.get());

                        pOutput.accept(ModBlocks.HYBRIDIZER.get());

                        pOutput.accept(ModItems.INDOM_FLASK.get());
                        pOutput.accept(ModItems.MAJUNGAR_FLASK.get());

                        pOutput.accept(ModItems.INDOM_SPAWN_EGG.get());
                        pOutput.accept(ModItems.MAJUNGAR_SPAWN_EGG.get());




                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
