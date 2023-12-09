package net.thevaliantsquidward.unusualhybrids.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.unusualhybrids.UnusualHybrids;
import net.thevaliantsquidward.unusualhybrids.entity.custom.IndominousRexEntity;
import software.bernie.geckolib.model.GeoModel;

public class IndominousRexModel extends GeoModel<IndominousRexEntity> {
    @Override
    public ResourceLocation getModelResource(IndominousRexEntity animatable) {
        return new ResourceLocation(UnusualHybrids.MOD_ID, "geo/indominous_rex.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(IndominousRexEntity animatable) {
        return new ResourceLocation(UnusualHybrids.MOD_ID, "textures/entity/indominous.png");
    }

    @Override
    public ResourceLocation getAnimationResource(IndominousRexEntity animatable) {
        return new ResourceLocation(UnusualHybrids.MOD_ID, "animations/indominous.animation.json");
    }


}