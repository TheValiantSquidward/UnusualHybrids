package net.thevaliantsquidward.unusualhybrids.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thevaliantsquidward.unusualhybrids.UnusualHybrids;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, UnusualHybrids.MOD_ID);



    public static final RegistryObject<SoundEvent> INDOM_BITE = createSoundEvent("indom_bite");
    public static final RegistryObject<SoundEvent> INDOM_DEATH = createSoundEvent("indom_death");
    public static final RegistryObject<SoundEvent> INDOM_HURT = createSoundEvent("indom_hurt");
    public static final RegistryObject<SoundEvent> INDOM_IDLE = createSoundEvent("indom_idle");
    private static RegistryObject<SoundEvent> createSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(UnusualHybrids.MOD_ID, name)));
    }
    public static void register(IEventBus eventBus) { SOUND_EVENTS.register(eventBus); }
}