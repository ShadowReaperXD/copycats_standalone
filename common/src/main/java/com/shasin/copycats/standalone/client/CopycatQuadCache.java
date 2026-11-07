package com.shasin.copycats.standalone.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Vanilla-backed quad cache for copycat rendering.
 * <p>
 * Cache key formula (per the workflow spec):
 * {@code (state.hashCode() ^ (shape.ordinal() << 8) ^ (connectionMask << 16))}
 */
public final class CopycatQuadCache {

    private static final Map<Long, List<CopycatQuadData>> CACHE = new HashMap<>();

    private CopycatQuadCache() {
    }

    public static long cacheKey(int stateHash, int shapeOrdinal, int connectionMask) {
        return stateHash ^ (shapeOrdinal << 8) ^ (connectionMask << 16);
    }

    public static List<CopycatQuadData> getCached(long key) {
        return CACHE.get(key);
    }

    public static void putCached(long key, List<CopycatQuadData> quads) {
        CACHE.put(key, quads);
    }

    public static void clear() {
        CACHE.clear();
    }

    public static int size() {
        return CACHE.size();
    }
}