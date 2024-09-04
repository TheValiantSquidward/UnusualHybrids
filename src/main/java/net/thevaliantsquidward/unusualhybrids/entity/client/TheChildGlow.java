package net.thevaliantsquidward.unusualhybrids.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.unusualhybrids.UnusualHybrids;
import net.thevaliantsquidward.unusualhybrids.entity.custom.IndominousRexEntity;
import net.thevaliantsquidward.unusualhybrids.entity.custom.TheChildEntity;
import net.thevaliantsquidward.unusualhybrids.entity.custom.TheChildEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class TheChildGlow extends GeoRenderLayer<TheChildEntity>  {
    private static final ResourceLocation OVERLAY = new ResourceLocation(UnusualHybrids.MOD_ID, "textures/entity/childglowmask.png");
    private static final ResourceLocation MODEL = new ResourceLocation(UnusualHybrids.MOD_ID, "geo/baby_indominous.geo.json");
    private static final ResourceLocation TEXTURE_WHITE = new ResourceLocation(UnusualHybrids.MOD_ID, "textures/entity/childglowmask.png");
    private static final ResourceLocation TEXTURE_BLACK = new ResourceLocation(UnusualHybrids.MOD_ID, "textures/entity/blackchildglowmask.png");


    public TheChildGlow(GeoRenderer<TheChildEntity> entityRendererIn) {
        super(entityRendererIn);

    }

    public ResourceLocation getOverlay(TheChildEntity entityLivingBaseIn) {
        return switch (entityLivingBaseIn.getVariant()) {
            case 1 -> TEXTURE_BLACK;
            default -> TEXTURE_WHITE;
        };
    }

    public void render(PoseStack poseStack, TheChildEntity entityLivingBaseIn, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        long roundedTime = entityLivingBaseIn.level().getDayTime() % 24000;
        boolean night = roundedTime >= 13000 && roundedTime <= 22000;
        if (night) {
            ResourceLocation overlayTexture = getOverlay(entityLivingBaseIn);
            RenderType cameo = RenderType.eyes(overlayTexture);
            getRenderer().reRender(this.getGeoModel().getBakedModel(MODEL), poseStack, bufferSource, entityLivingBaseIn, renderType,
                    bufferSource.getBuffer(cameo), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                    1, 1, 1, 1);
        }
    }

}
