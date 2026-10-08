package com.example.client;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class CustomBlockOverlay {
    public static void render(WorldRenderContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.crosshairTarget == null || client.crosshairTarget.getType() != HitResult.Type.BLOCK) {
            return;
        }

        BlockHitResult hitResult = (BlockHitResult) client.crosshairTarget;
        BlockPos pos = hitResult.getBlockPos();
        Vec3d cameraPos = context.camera().getPos();

        MatrixStack matrices = context.matrixStack();
        matrices.push();
        matrices.translate(pos.getX() - cameraPos.x, pos.getY() - cameraPos.y, pos.getZ() - cameraPos.z);

        drawBox(matrices, new Box(0, 0, 0, 1, 1, 1));

        matrices.pop();
    }

    private static void drawBox(MatrixStack matrices, Box box) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

        float r = 0.0f, g = 0.8f, b = 1.0f, a = 0.4f;

        // Грани куба
        buffer.vertex(matrices.peek().getPositionMatrix(), (float)box.minX, (float)box.minY, (float)box.minZ).color(r, g, b, a);
        buffer.vertex(matrices.peek().getPositionMatrix(), (float)box.maxX, (float)box.minY, (float)box.minZ).color(r, g, b, a);
        buffer.vertex(matrices.peek().getPositionMatrix(), (float)box.maxX, (float)box.maxY, (float)box.minZ).color(r, g, b, a);
        buffer.vertex(matrices.peek().getPositionMatrix(), (float)box.minX, (float)box.maxY, (float)box.minZ).color(r, g, b, a);

        buffer.vertex(matrices.peek().getPositionMatrix(), (float)box.minX, (float)box.minY, (float)box.maxZ).color(r, g, b, a);
        buffer.vertex(matrices.peek().getPositionMatrix(), (float)box.maxX, (float)box.minY, (float)box.maxZ).color(r, g, b, a);
        buffer.vertex(matrices.peek().getPositionMatrix(), (float)box.maxX, (float)box.maxY, (float)box.maxZ).color(r, g, b, a);
        buffer.vertex(matrices.peek().getPositionMatrix(), (float)box.minX, (float)box.maxY, (float)box.maxZ).color(r, g, b, a);

        BufferRenderer.drawWithGlobalProgram(buffer.end());
    }
          }

