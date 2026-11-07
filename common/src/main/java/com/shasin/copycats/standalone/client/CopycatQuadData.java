package com.shasin.copycats.standalone.client;

/**
 * Generic holder for baked quad vertex data. Platform modules convert this
 * into loader-specific {@link net.minecraft.client.renderer.model.BakedQuad} instances.
 */
public record CopycatQuadData(float[] vertices, int tintIndex, int faceOrdinal, boolean shade) {
}