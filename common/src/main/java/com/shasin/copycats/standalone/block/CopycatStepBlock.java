package com.shasin.copycats.standalone.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Step copycat: half depth, volume ratio {@code 0.5f}.
 */
public class CopycatStepBlock extends CopycatBlock {

    public CopycatStepBlock(BlockBehaviour.Properties properties) {
        super(properties, STEP, CopycatShape.STEP);
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