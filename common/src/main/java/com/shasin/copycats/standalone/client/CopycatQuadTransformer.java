package com.shasin.copycats.standalone.client;

import com.shasin.copycats.standalone.block.CopycatBlock;
import com.shasin.copycats.standalone.block.CopycatShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import java.util.ArrayList;
import java.util.List;

/**
 * Core quad transformer for copycat rendering. Produces generic
 * {@link CopycatQuadData} that platform modules convert to {@link net.minecraft.client.renderer.model.BakedQuad}.
 * <p>
 * All visual rendering happens inside {@link net.minecraft.client.renderer.model.BakedModel#getQuads}
 * (chunk-building threads), never in a BlockEntityRenderer.
 * <p>
 * Quad cache key formula (per the workflow spec):
 * {@code (targetBlockState.hashCode() ^ (shape.ordinal() << 8) ^ (connectionMask << 16))}
 */
public final class CopycatQuadTransformer {

    private CopycatQuadTransformer() {
    }

    /**
     * Seamless connection mask. Checks the 6 orthogonal directions and sets bit
     * {@code 1 << direction.ordinal()} when the neighbour is the same Copycat block
     * or holds the exact same target {@link BlockState}.
     */
    public static int computeConnectionMask(BlockGetter level, BlockPos pos, BlockState state) {
        int mask = 0;
        for (Direction direction : Direction.values()) {
            BlockPos neighbour = pos.relative(direction);
            BlockState neighbourState = level.getBlockState(neighbour);
            boolean matches =
                    neighbourState.getBlock() instanceof CopycatBlock ||
                    neighbourState.equals(state);
            if (matches) {
                mask |= 1 << direction.ordinal();
            }
        }
        return mask;
    }

    /**
     * Build (or retrieve from cache) the generic quad data for a copycat block state.
     */
    public static List<CopycatQuadData> buildQuads(BlockState state, CopycatShape shape, int connectionMask) {
        long key = CopycatQuadCache.cacheKey(state.hashCode(), shape.ordinal(), connectionMask);
        List<CopycatQuadData> cached = CopycatQuadCache.getCached(key);
        if (cached != null) {
            return cached;
        }

        List<CopycatQuadData> quads = new ArrayList<>(6);
        VoxelQuadBuilder.appendShapeQuads(shape, quads, connectionMask);

        CopycatQuadCache.putCached(key, quads);
        return quads;
    }

    public static void clear() {
        CopycatQuadCache.clear();
    }

    public static int size() {
        return CopycatQuadCache.size();
    }
}