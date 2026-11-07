package com.shasin.copycats.standalone.api;

import net.minecraft.world.level.block.state.BlockState;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Central registry for copycat data providers.
 * <p>
 * Mass providers are functions {@code BlockState -> Float}. The first non-null
 * result wins; otherwise a default density of {@code 1.0f} is assumed.
 */
public final class CopycatAPI {
    private static final List<Function<BlockState, Float>> MASS_PROVIDERS = new ArrayList<>();

    private CopycatAPI() {
    }

    /**
     * Register a mass provider. External mods call this to inject custom mass/physics data.
     */
    public static void registerMassProvider(Function<BlockState, Float> provider) {
        MASS_PROVIDERS.add(provider);
    }

    /**
     * Register an {@link ICopycatDataProvider} as a mass provider.
     */
    public static void registerDataProvider(ICopycatDataProvider provider) {
        registerMassProvider(provider::apply);
    }

    /**
     * Compute the material mass for the given block state.
     *
     * @return the mass in arbitrary density units, or {@code 1.0f} if no provider applies.
     * @throws NullPointerException never; returns the fallback density for air / unknown states.
     */
    public static float getMaterialMass(BlockState state) {
        if (state == null) {
            return 1.0f;
        }
        for (var provider : MASS_PROVIDERS) {
            Float mass = provider.apply(state);
            if (mass != null) {
                return mass;
            }
        }
        return 1.0f; // Default fallback density
    }
}