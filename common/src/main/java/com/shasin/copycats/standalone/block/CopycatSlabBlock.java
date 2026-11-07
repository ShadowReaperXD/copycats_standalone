package com.shasin.copycats.standalone.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Slab copycat: bottom half of a block, volume ratio {@code 0.5f}.
 */
public class CopycatSlabBlock extends CopycatBlock {

    public CopycatSlabBlock(BlockBehaviour.Properties properties) {
        super(properties, SLAB, CopycatShape.SLAB);
    }

    @Override
    public float getVolumeRatio() {
        return 0.5f;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        CopycatBlockEntity blockEntity = new CopycatBlockEntity(pos, state);
        blockEntity.setVolumeRatio(0.5f);
        return blockEntity;
    }
}