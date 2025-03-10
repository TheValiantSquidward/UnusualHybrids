package net.thevaliantsquidward.unusualhybrids.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.unusualhybrids.UnusualHybrids;
import net.thevaliantsquidward.unusualhybrids.entity.custom.OldMajungaraptorEntity;
import software.bernie.geckolib.model.GeoModel;

public class MajungaraptorModel extends GeoModel<OldMajungaraptorEntity> {
    @Override
    public ResourceLocation getModelResource(OldMajungaraptorEntity animatable) {
        return new ResourceLocation(UnusualHybrids.MOD_ID, "geo/majungaraptor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(OldMajungaraptorEntity animatable) {
        return new ResourceLocation(UnusualHybrids.MOD_ID, "textures/entity/majungar.png");
    }

    @Override
    public ResourceLocation getAnimationResource(OldMajungaraptorEntity animatable) {
        return new ResourceLocation(UnusualHybrids.MOD_ID, "animations/majungaraptor.animation.json");
    }
}