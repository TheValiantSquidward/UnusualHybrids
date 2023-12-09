package net.thevaliantsquidward.unusualhybrids.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.unusualhybrids.UnusualHybrids;
import net.thevaliantsquidward.unusualhybrids.entity.custom.IndominousRexEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class IndominousRexEepyLayer extends GeoRenderLayer<IndominousRexEntity> {
    private static final ResourceLocation OVERLAY = new ResourceLocation(UnusualHybrids.MOD_ID, "textures/entity/indominouseepy.png");
    private static final ResourceLocation MODEL = new ResourceLocation(UnusualHybrids.MOD_ID, "geo/indominous_rex.geo.json");

    public IndominousRexEepyLayer(GeoRenderer<IndominousRexEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, IndominousRexEntity entityLivingBaseIn, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (entityLivingBaseIn.hasEepy()) {
            RenderType cameo = RenderType.entityCutout(OVERLAY);
            getRenderer().reRender(this.getGeoModel().getBakedModel(MODEL), poseStack, bufferSource, entityLivingBaseIn, renderType,
                    bufferSource.getBuffer(cameo), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                    1, 1, 1, 1);
        }
    }


}