package com.sekai.potionmixer.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.sekai.potionmixer.Main;
import com.sekai.potionmixer.menu.MixingStandMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MixingStandScreen extends AbstractContainerScreen<MixingStandMenu> {
    private static final ResourceLocation MIXING_STAND_LOCATION = new ResourceLocation(Main.MODID, "textures/gui/container/mixing_stand.png");
    private static final int[] BUBBLELENGTHS = new int[]{29, 24, 20, 16, 11, 6, 0};

    public MixingStandScreen(MixingStandMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    public void render(PoseStack p_98341_, int p_98342_, int p_98343_, float p_98344_) {
        this.renderBackground(p_98341_);
        super.render(p_98341_, p_98342_, p_98343_, p_98344_);
        this.renderTooltip(p_98341_, p_98342_, p_98343_);
    }

    protected void renderBg(PoseStack p_98336_, float p_98337_, int p_98338_, int p_98339_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, MIXING_STAND_LOCATION);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(p_98336_, i, j, 0, 0, this.imageWidth, this.imageHeight);

        int i1 = this.menu.getBrewingTicks();
        if (i1 > 0) {
            int j1 = (int)(28.0F * (1.0F - (float)i1 / 400.0F));
            if (j1 > 0) {
                this.blit(p_98336_, i + 97, j + 44, 176, 0, 9, j1);
            }
        }

    }
}