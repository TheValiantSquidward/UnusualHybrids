package net.thevaliantsquidward.unusualhybrids.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.thevaliantsquidward.unusualhybrids.UnusualHybrids;
import net.thevaliantsquidward.unusualhybrids.entity.ModEntities;
import net.thevaliantsquidward.unusualhybrids.entity.custom.IndominousRexEntity;
import net.thevaliantsquidward.unusualhybrids.entity.custom.MajungaraptorEntity;
import net.thevaliantsquidward.unusualhybrids.entity.custom.TheChildEntity;
import net.thevaliantsquidward.unusualhybrids.item.ModItems;
import net.thevaliantsquidward.unusualhybrids.item.custom.FusionScytheItem;


@Mod.EventBusSubscriber(modid = UnusualHybrids.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {



    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.INDOM.get(), IndominousRexEntity.createAttributes().build());
        event.put(ModEntities.MAJUNGARAPTOR.get(), MajungaraptorEntity.createAttributes().build());
        event.put(ModEntities.CHILD.get(), TheChildEntity.createAttributes().build());

    }
}