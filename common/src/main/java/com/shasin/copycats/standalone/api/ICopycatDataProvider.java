package com.shasin.copycats.standalone.api;

import com.shasin.copycats.standalone.block.CopycatBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import java.util.function.Function;

/**
 * Decoupled SPI for external mod data injection (e.g. mass / physics calculations).
 * <p>
 * Implementations are registered through {@link CopycatAPI} and queried by
 * {@link CopycatBlockEntity} when computing a block's mass.
 */
@FunctionalInterface
public interface ICopycatDataProvider extends Function<BlockState, Float> {
    @Override
    Float apply(BlockState state);
}