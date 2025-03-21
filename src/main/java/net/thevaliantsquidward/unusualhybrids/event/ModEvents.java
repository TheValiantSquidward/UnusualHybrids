package net.thevaliantsquidward.unusualhybrids.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.thevaliantsquidward.unusualhybrids.UnusualHybrids;
import net.thevaliantsquidward.unusualhybrids.entity.ModEntities;
import net.thevaliantsquidward.unusualhybrids.entity.custom.IndominousRexEntity;
import net.thevaliantsquidward.unusualhybrids.entity.custom.OldMajungaraptorEntity;
import net.thevaliantsquidward.unusualhybrids.entity.custom.TheChildEntity;


@Mod.EventBusSubscriber(modid = UnusualHybrids.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {



    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.INDOM.get(), IndominousRexEntity.createAttributes().build());
        event.put(ModEntities.MAJUNGARAPTOR.get(), OldMajungaraptorEntity.createAttributes().build());
        event.put(ModEntities.CHILD.get(), TheChildEntity.createAttributes().build());

    }
}