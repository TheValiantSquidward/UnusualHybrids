package net.thevaliantsquidward.unusualhybrids.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.thevaliantsquidward.unusualhybrids.UnusualHybrids;

public class HybridizerScreen extends AbstractContainerScreen<HybridizerMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(UnusualHybrids.MOD_ID, "textures/gui/hybridizer_gui.png");

    public HybridizerScreen(HybridizerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
        renderFuelMeter(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 80, y + 7, 178, 0, 18, menu.getScaledProgress());
        }
    }

    private void renderFuelMeter(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isFueled()) {

            guiGraphics.blit(TEXTURE, x + 100, y + 61, 196, 0, menu.getScaledFuel(), 11);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
