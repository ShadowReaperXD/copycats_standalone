package com.shasin.copycats.standalone.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Central holder for the standalone copycat blocks. Instances are created here
 * (common) and registered by the platform modules using their native registry API.
 */
public final class CopycatBlocks {
    private CopycatBlocks() {
    }

    public static final Block COPYCAT_BLOCK = new CopycatBlock(
            BlockBehaviour.Properties.of(), CopycatBlock.FULL, CopycatShape.FULL) {
    };
    public static final Block COPYCAT_SLAB = new CopycatSlabBlock(BlockBehaviour.Properties.of());
    public static final Block COPYCAT_BYTE = new CopycatByteBlock(BlockBehaviour.Properties.of());
    public static final Block COPYCAT_STEP = new CopycatStepBlock(BlockBehaviour.Properties.of());
    public static final Block COPYCAT_VERTICAL_STEP = new CopycatVerticalStepBlock(BlockBehaviour.Properties.of());
}