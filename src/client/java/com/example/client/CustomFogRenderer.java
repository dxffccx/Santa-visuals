package com.example.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;

public class CustomFogRenderer {

    public static void applyCustomFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog) {
        if (!SantaVisuals.customFogEnabled) return;

        RenderSystem.setShaderFogStart(2.0f);
        RenderSystem.setShaderFogEnd(24.0f);
        RenderSystem.setShaderFogColor(0.1f, 0.05f, 0.2f, 1.0f);
    }
}
