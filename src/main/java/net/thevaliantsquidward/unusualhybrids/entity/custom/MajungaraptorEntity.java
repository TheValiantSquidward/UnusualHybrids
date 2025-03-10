package net.thevaliantsquidward.unusualhybrids.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.RawAnimation;

public class MajungaraptorEntity extends EntityClimber {

    private static final RawAnimation MAJUNGAR_BELLOW = RawAnimation.begin().thenLoop("animation.majungaraptor.bellow");
    private static final RawAnimation MAJUNGAR_SCRATCH1 = RawAnimation.begin().thenLoop("animation.majungaraptor.scratch1");
    private static final RawAnimation MAJUNGAR_SCRATCH2 = RawAnimation.begin().thenLoop("animation.majungaraptor.scratch2");

    private static final RawAnimation MAJUNGAR_WALK = RawAnimation.begin().thenLoop("animation.majungaraptor.walk");
    private static final RawAnimation MAJUNGAR_IDLE = RawAnimation.begin().thenLoop("animation.majungaraptor.idle");
    private static final RawAnimation MAJUNGAR_ATTACK = RawAnimation.begin().thenLoop("animation.majungaraptor.attack");
    private static final RawAnimation MAJUNGAR_SWIM = RawAnimation.begin().thenLoop("animation.majungaraptor.swim");
    private static final RawAnimation MAJUNGAR_STUN = RawAnimation.begin().thenLoop("animation.majungaraptor.stunned");
    private static final RawAnimation MAJUNGAR_RUN = RawAnimation.begin().thenLoop("animation.majungaraptor.run");
    private static final RawAnimation MAJUNGAR_CLIMB = RawAnimation.begin().thenLoop("animation.majungaraptor.climb");
    private static final RawAnimation MAJUNGAR_FALL = RawAnimation.begin().thenLoop("animation.majungaraptor.fall");


    public MajungaraptorEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    
}
