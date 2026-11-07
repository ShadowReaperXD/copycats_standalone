package com.shasin.copycats.standalone;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Standalone Copycats mod core.
 * <p>
 * Zero external modding-framework dependencies (no Create, Flywheel, Registrate, Ponder, owo-lib).
 * The only external library used is FastUtil (for the copycat quad cache).
 */
public final class Copycats {
    public static final String MODID = "copycats";
    public static final String NAME = "Copycats";
    public static final Logger LOGGER = LoggerFactory.getLogger("Copycats");

    private Copycats() {
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public static void init() {
        // Platform modules perform registration and then invoke this hook.
    }
}