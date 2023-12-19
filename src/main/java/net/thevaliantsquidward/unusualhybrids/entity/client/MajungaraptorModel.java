package net.thevaliantsquidward.unusualhybrids.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.unusualhybrids.UnusualHybrids;
import net.thevaliantsquidward.unusualhybrids.entity.custom.IndominousRexEntity;
import net.thevaliantsquidward.unusualhybrids.entity.custom.MajungaraptorEntity;
import software.bernie.geckolib.model.GeoModel;

public class MajungaraptorModel extends GeoModel<MajungaraptorEntity> {
    @Override
    public ResourceLocation getModelResource(MajungaraptorEntity animatable) {
        return new ResourceLocation(UnusualHybrids.MOD_ID, "geo/majungaraptor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MajungaraptorEntity animatable) {
        return new ResourceLocation(UnusualHybrids.MOD_ID, "textures/entity/majungar.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MajungaraptorEntity animatable) {
        return new ResourceLocation(UnusualHybrids.MOD_ID, "animations/majungaraptor.animation.json");
    }
}