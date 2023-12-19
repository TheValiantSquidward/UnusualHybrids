package net.thevaliantsquidward.unusualhybrids.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.unusualhybrids.UnusualHybrids;
import net.thevaliantsquidward.unusualhybrids.entity.custom.IndominousRexEntity;
import net.thevaliantsquidward.unusualhybrids.entity.custom.MajungaraptorEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MajungaraptorRenderer extends GeoEntityRenderer<MajungaraptorEntity> {
    public MajungaraptorRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MajungaraptorModel());
        this.addRenderLayer(new MajungarGlow(this));
    }

    @Override
    public ResourceLocation getTextureLocation(MajungaraptorEntity animatable) {
        return new ResourceLocation(UnusualHybrids.MOD_ID, "textures/entity/majungar.png");
    }

    @Override
    public void render(MajungaraptorEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.4f, 0.4f, 0.4f);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}