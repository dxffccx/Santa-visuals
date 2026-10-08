package com.example.client;

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

        for (var entity : client.world.getEntities()) {
            if (entity instanceof LivingEntity && entity != client.player && entity.isAlive()) {
                Vec3d pos = entity.getPos();
                Vec3d cameraPos = context.camera().getPos();

                MatrixStack matrices = context.matrixStack();
                matrices.push();
                matrices.translate(pos.x - cameraPos.x, pos.y - cameraPos.y, pos.z - cameraPos.z);

                drawEntityBox(matrices, entity.getWidth(), entity.getHeight());

                matrices.pop();
            }
        }
    }

    private static void drawEntityBox(MatrixStack matrices, float width, float height) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);

        float halfWidth = width / 2.0f;

        // Нижний квадрат
        buffer.vertex(matrices.peek().getPositionMatrix(), -halfWidth, 0, -halfWidth).color(1.0f, 0.0f, 0.0f, 1.0f);
        buffer.vertex(matrices.peek().getPositionMatrix(), halfWidth, 0, -halfWidth).color(1.0f, 0.0f, 0.0f, 1.0f);
        buffer.vertex(matrices.peek().getPositionMatrix(), halfWidth, 0, -halfWidth).color(1.0f, 0.0f, 0.0f, 1.0f);
        buffer.vertex(matrices.peek().getPositionMatrix(), halfWidth, 0, halfWidth).color(1.0f, 0.0f, 0.0f, 1.0f);
        buffer.vertex(matrices.peek().getPositionMatrix(), halfWidth, 0, halfWidth).color(1.0f, 0.0f, 0.0f, 1.0f);
        buffer.vertex(matrices.peek().getPositionMatrix(), -halfWidth, 0, halfWidth).color(1.0f, 0.0f, 0.0f, 1.0f);
        buffer.vertex(matrices.peek().getPositionMatrix(), -halfWidth, 0, halfWidth).color(1.0f, 0.0f, 0.0f, 1.0f);
        buffer.vertex(matrices.peek().getPositionMatrix(), -halfWidth, 0, -halfWidth).color(1.0f, 0.0f, 0.0f, 1.0f);

        // Верхний квадрат
        buffer.vertex(matrices.peek().getPositionMatrix(), -halfWidth, height, -halfWidth).color(1.0f, 0.0f, 0.0f, 1.0f);
        buffer.vertex(matrices.peek().getPositionMatrix(), halfWidth, height, -halfWidth).color(1.0f, 0.0f, 0.0f, 1.0f);
        buffer.vertex(matrices.peek().getPositionMatrix(), halfWidth, height, -halfWidth).color(1.0f, 0.0f, 0.0f, 1.0f);
        buffer.vertex(matrices.peek().getPositionMatrix(), halfWidth, height, halfWidth).color(1.0f, 0.0f, 0.0f, 1.0f);
        buffer.vertex(matrices.peek().getPositionMatrix(), halfWidth, height, halfWidth).color(1.0f, 0.0f, 0.0f, 1.0f);
        buffer.vertex(matrices.peek().getPositionMatrix(), -halfWidth, height, halfWidth).color(1.0f, 0.0f, 0.0f, 1.0f);
        buffer.vertex(matrices.peek().getPositionMatrix(), -halfWidth, height, halfWidth).color(1.0f, 0.0f, 0.0f, 1.0f);
        buffer.vertex(matrices.peek().getPositionMatrix(), -halfWidth, height, -halfWidth).color(1.0f, 0.0f, 0.0f, 1.0f);

        // Вертикальные линии
        buffer.vertex(matrices.peek().getPositionMatrix(), -halfWidth, 0, -halfWidth).color(1.0f, 0.0f, 0.0f, 1.0f);
        buffer.vertex(matrices.peek().getPositionMatrix(), -halfWidth, height, -halfWidth).color(1.0f, 0.0f, 0.0f, 1.0f);
        buffer.vertex(matrices.peek().getPositionMatrix(), halfWidth, 0, -halfWidth).color(1.0f, 0.0f, 0.0f, 1.0f);
        buffer.vertex(matrices.peek().getPositionMatrix(), halfWidth, height, -halfWidth).color(1.0f, 0.0f, 0.0f, 1.0f);
        buffer.vertex(matrices.peek().getPositionMatrix(), halfWidth, 0, halfWidth).color(1.0f, 0.0f, 0.0f, 1.0f);
        buffer.vertex(matrices.peek().getPositionMatrix(), halfWidth, height, halfWidth).color(1.0f, 0.0f, 0.0f, 1.0f);
        buffer.vertex(matrices.peek().getPositionMatrix(), -halfWidth, 0, halfWidth).color(1.0f, 0.0f, 0.0f, 1.0f);
        buffer.vertex(matrices.peek().getPositionMatrix(), -halfWidth, height, halfWidth).color(1.0f, 0.0f, 0.0f, 1.0f);

        BufferRenderer.drawWithGlobalProgram(buffer.end());
    }
                      }

