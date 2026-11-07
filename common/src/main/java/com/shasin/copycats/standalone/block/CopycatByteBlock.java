package com.shasin.copycats.standalone.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Byte copycat: one eighth of a block, volume ratio {@code 0.125f}.
 */
public class CopycatByteBlock extends CopycatBlock {

    public CopycatByteBlock(BlockBehaviour.Properties properties) {
        super(properties, BYTE, CopycatShape.BYTE);
    }

    @Override
    public float getVolumeRatio() {
        return 0.125f;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        CopycatBlockEntity blockEntity = new CopycatBlockEntity(pos, state);
        blockEntity.setVolumeRatio(0.125f);
        return blockEntity;
    }
}