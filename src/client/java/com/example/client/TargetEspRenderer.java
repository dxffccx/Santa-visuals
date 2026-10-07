package com.example.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

public class TargetEspRenderer {

    public static void render(WorldRenderContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;

        Vec3d cameraPos = context.camera().getPos();
        MatrixStack matrices = context.matrixStack();

        for (Object entityObj : client.world.getEntities()) {
            if (entityObj instanceof LivingEntity entity && entity != client.player) {
                Vec3d pos = entity.getPos();
                
                matrices.push();
                matrices.translate(pos.x - cameraPos.x, pos.y - cameraPos.y, pos.z - cameraPos.z);

                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.disableCull();

                drawEntityBox(matrices, entity.getWidth(), entity.getHeight());

                RenderSystem.enableCull();
                RenderSystem.disableBlend();
                matrices.pop();
            }
        }
    }

    private static void drawEntityBox(MatrixStack matrices, float width, float height) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);

        float halfWidth = width / 2.0f;
        WorldRenderer.drawBox(matrices, buffer, -halfWidth, 0, -halfWidth, halfWidth, height, halfWidth, 1.0f, 0.0f, 0.0f, 1.0f);
        BufferRenderer.drawWithGlobalProgram(buffer.end());
    }
}
