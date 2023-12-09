package net.thevaliantsquidward.unusualhybrids.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.unusualhybrids.UnusualHybrids;
import net.thevaliantsquidward.unusualhybrids.entity.custom.IndominousRexEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class IndominousRexRenderer extends GeoEntityRenderer<IndominousRexEntity> {
    private static final ResourceLocation TEXTURE_WHITE = new ResourceLocation(UnusualHybrids.MOD_ID, "textures/entity/indominous.png");
    private static final ResourceLocation TEXTURE_BLACK = new ResourceLocation(UnusualHybrids.MOD_ID, "textures/entity/blackindominous.png");


    public IndominousRexRenderer(EntityRendererProvider.Context context) {
        super(context, new IndominousRexModel());
        this.addRenderLayer(new IndominousRexEepyLayer(this));
        this.addRenderLayer(new IndominousGlow(this));
    }

    protected void scale(IndominousRexEntity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
    }

    public ResourceLocation getTextureLocation(IndominousRexEntity entity) {
        return switch (entity.getVariant()) {
            case 1 -> TEXTURE_BLACK;
            default -> TEXTURE_WHITE;
        };
    }
}