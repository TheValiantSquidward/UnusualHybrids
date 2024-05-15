package net.thevaliantsquidward.unusualhybrids.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.unusualhybrids.UnusualHybrids;
import net.thevaliantsquidward.unusualhybrids.entity.custom.MajungaraptorEntity;
import net.thevaliantsquidward.unusualhybrids.entity.custom.TheChildEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TheChildRenderer extends GeoEntityRenderer<TheChildEntity> {
    public TheChildRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TheChildModel());
        this.addRenderLayer(new TheChildGlow(this));
    }

    @Override
    public ResourceLocation getTextureLocation(TheChildEntity animatable) {
        return new ResourceLocation(UnusualHybrids.MOD_ID, "textures/entity/indominous_baby.png");
    }

    @Override
    public void render(TheChildEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.4f, 0.4f, 0.4f);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}