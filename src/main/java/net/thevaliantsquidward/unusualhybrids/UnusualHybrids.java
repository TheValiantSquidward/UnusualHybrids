package net.thevaliantsquidward.unusualhybrids;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.thevaliantsquidward.unusualhybrids.entity.ModEntities;
import net.thevaliantsquidward.unusualhybrids.entity.client.IndominousRexRenderer;
import net.thevaliantsquidward.unusualhybrids.entity.client.MajungaraptorRenderer;
import net.thevaliantsquidward.unusualhybrids.entity.client.TheChildRenderer;
import net.thevaliantsquidward.unusualhybrids.entity.custom.IndominousRexEntity;
import net.thevaliantsquidward.unusualhybrids.item.ModCreativeModeTabs;
import net.thevaliantsquidward.unusualhybrids.item.ModItems;
import net.thevaliantsquidward.unusualhybrids.item.custom.FusionScytheItem;
import net.thevaliantsquidward.unusualhybrids.sound.ModSounds;
import org.slf4j.Logger;


@Mod(UnusualHybrids.MOD_ID)
public class UnusualHybrids
{
    public static final String MOD_ID = "unusualhybrids";
    private static final Logger LOGGER = LogUtils.getLogger();

    public UnusualHybrids()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        ModEntities.register(modEventBus);

        ModItems.register(modEventBus);

        ModSounds.register(modEventBus);

        ModCreativeModeTabs.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register
                    (ModEntities.INDOM.get(), IndominousRexRenderer:: new);
            EntityRenderers.register
                    (ModEntities.MAJUNGARAPTOR.get(), MajungaraptorRenderer:: new);
            EntityRenderers.register
                    (ModEntities.CHILD.get(), TheChildRenderer:: new);
        }
    }
}