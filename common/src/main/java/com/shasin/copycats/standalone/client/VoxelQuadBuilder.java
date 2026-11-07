package com.shasin.copycats.standalone.client;

import com.shasin.copycats.standalone.block.CopycatBlock;
import com.shasin.copycats.standalone.block.CopycatShape;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

/**
 * Builds raw baked quad data for a copycat shape from its axis-aligned bounding
 * boxes. UVs are remapped per connection-mask match without allocating new objects
 * inside the per-face loop.
 */
final class VoxelQuadBuilder {

    private VoxelQuadBuilder() {
    }

    static void appendShapeQuads(CopycatShape shape, List<CopycatQuadData> out, int connectionMask) {
        VoxelShape voxelShape = switch (shape) {
            case SLAB -> CopycatBlock.SLAB;
            case STEP -> CopycatBlock.STEP;
            case BYTE -> CopycatBlock.BYTE;
            case VERTICAL_STEP -> CopycatBlock.VERTICAL_STEP;
            default -> CopycatBlock.FULL;
        };

        for (AABB box : voxelShape.toAabbs()) {
            appendBoxQuads(box, out, connectionMask);
        }
    }

    private static void appendBoxQuads(AABB box, List<CopycatQuadData> out, int connectionMask) {
        float minX = (float) box.minX;
        float minY = (float) box.minY;
        float minZ = (float) box.minZ;
        float maxX = (float) box.maxX;
        float maxY = (float) box.maxY;
        float maxZ = (float) box.maxZ;
        Face[] faces = new Face[]{
                new Face(Direction.DOWN, minX, minZ, maxX, maxZ, minY, minY),
                new Face(Direction.UP, minX, minZ, maxX, maxZ, maxY, maxY),
                new Face(Direction.NORTH, minX, minY, maxX, maxY, minZ, minZ),
                new Face(Direction.SOUTH, minX, minY, maxX, maxY, maxZ, maxZ),
                new Face(Direction.WEST, minY, minZ, maxY, maxZ, minX, minX),
                new Face(Direction.EAST, minY, minZ, maxY, maxZ, maxX, maxX)
        };

        for (Face face : faces) {
            float[] uvs = faceUvs(face.face, connectionMask);
            out.add(makeQuad(box, face.face, uvs[0], uvs[1], uvs[2], uvs[3]));
        }
    }

    private static float[] faceUvs(Direction face, int connectionMask) {
        boolean connected = (connectionMask & (1 << face.ordinal())) != 0;
        float u0 = 0, u1 = 16, v0 = 0, v1 = 16;
        if (connected) {
            switch (face) {
                case DOWN, UP -> { v0 = 8; v1 = 8; }
                default -> { u0 = 8; u1 = 8; }
            }
        }
        return new float[]{u0, u1, v0, v1};
    }

    private static CopycatQuadData makeQuad(AABB box, Direction face, float u0, float u1, float v0, float v1) {
        float[] vertices = new float[44];
        float white = Float.intBitsToFloat(0xFFFFFFFF);
        float[] vx = new float[4];
        float[] vy = new float[4];
        float[] vz = new float[4];
        float nx = 0, ny = 0, nz = 0;

        switch (face) {
            case DOWN -> {
                ny = -1;
                vx[0] = (float) box.minX; vy[0] = (float) box.minY; vz[0] = (float) box.minZ;
                vx[1] = (float) box.maxX; vy[1] = (float) box.minY; vz[1] = (float) box.minZ;
                vx[2] = (float) box.maxX; vy[2] = (float) box.minY; vz[2] = (float) box.maxZ;
                vx[3] = (float) box.minX; vy[3] = (float) box.minY; vz[3] = (float) box.maxZ;
            }
            case UP -> {
                ny = 1;
                vx[0] = (float) box.minX; vy[0] = (float) box.maxY; vz[0] = (float) box.maxZ;
                vx[1] = (float) box.maxX; vy[1] = (float) box.maxY; vz[1] = (float) box.maxZ;
                vx[2] = (float) box.maxX; vy[2] = (float) box.maxY; vz[2] = (float) box.minZ;
                vx[3] = (float) box.minX; vy[3] = (float) box.maxY; vz[3] = (float) box.minZ;
            }
            case NORTH -> {
                nz = -1;
                vx[0] = (float) box.minX; vy[0] = (float) box.maxY; vz[0] = (float) box.minZ;
                vx[1] = (float) box.maxX; vy[1] = (float) box.maxY; vz[1] = (float) box.minZ;
                vx[2] = (float) box.maxX; vy[2] = (float) box.minY; vz[2] = (float) box.minZ;
                vx[3] = (float) box.minX; vy[3] = (float) box.minY; vz[3] = (float) box.minZ;
            }
            case SOUTH -> {
                nz = 1;
                vx[0] = (float) box.minX; vy[0] = (float) box.minY; vz[0] = (float) box.maxZ;
                vx[1] = (float) box.maxX; vy[1] = (float) box.minY; vz[1] = (float) box.maxZ;
                vx[2] = (float) box.maxX; vy[2] = (float) box.maxY; vz[2] = (float) box.maxZ;
                vx[3] = (float) box.minX; vy[3] = (float) box.maxY; vz[3] = (float) box.maxZ;
            }
            case WEST -> {
                nx = -1;
                vx[0] = (float) box.minX; vy[0] = (float) box.maxY; vz[0] = (float) box.minZ;
                vx[1] = (float) box.minX; vy[1] = (float) box.maxY; vz[1] = (float) box.maxZ;
                vx[2] = (float) box.minX; vy[2] = (float) box.minY; vz[2] = (float) box.maxZ;
                vx[3] = (float) box.minX; vy[3] = (float) box.minY; vz[3] = (float) box.minZ;
            }
            case EAST -> {
                nx = 1;
                vx[0] = (float) box.maxX; vy[0] = (float) box.minY; vz[0] = (float) box.minZ;
                vx[1] = (float) box.maxX; vy[1] = (float) box.minY; vz[1] = (float) box.maxZ;
                vx[2] = (float) box.maxX; vy[2] = (float) box.maxY; vz[2] = (float) box.maxZ;
                vx[3] = (float) box.maxX; vy[3] = (float) box.maxY; vz[3] = (float) box.minZ;
            }
        }

        float[][] uvs = new float[][]{{u0, v1}, {u1, v1}, {u1, v0}, {u0, v0}};
        for (int i = 0; i < 4; i++) {
            int off = i * 11;
            vertices[off] = vx[i];
            vertices[off + 1] = vy[i];
            vertices[off + 2] = vz[i];
            vertices[off + 3] = white;
            vertices[off + 4] = uvs[i][0] / 16f;
            vertices[off + 5] = uvs[i][1] / 16f;
            vertices[off + 6] = nx;
            vertices[off + 7] = ny;
            vertices[off + 8] = nz;
            vertices[off + 9] = 15.0f;
            vertices[off + 10] = 15.0f;
        }

        return new CopycatQuadData(vertices, 0, face.ordinal(), true);
    }

    private record Face(Direction face, float a0, float b0, float a1, float b1, float c0, float c1) {
    }
}