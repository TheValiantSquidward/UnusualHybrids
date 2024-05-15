package net.thevaliantsquidward.unusualhybrids.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.unusualhybrids.UnusualHybrids;
import net.thevaliantsquidward.unusualhybrids.entity.custom.MajungaraptorEntity;
import net.thevaliantsquidward.unusualhybrids.entity.custom.TheChildEntity;
import software.bernie.geckolib.model.GeoModel;

public class TheChildModel extends GeoModel<TheChildEntity> {
    @Override
    public ResourceLocation getModelResource(TheChildEntity animatable) {
        return new ResourceLocation(UnusualHybrids.MOD_ID, "geo/baby_indominous.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TheChildEntity animatable) {
        return new ResourceLocation(UnusualHybrids.MOD_ID, "textures/entity/indominous_baby.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TheChildEntity animatable) {
        return new ResourceLocation(UnusualHybrids.MOD_ID, "animations/indominousbaby.animation.json");
    }
}
