package com.shasin.copycats.standalone.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Vertical step copycat: half width, volume ratio {@code 0.5f}.
 */
public class CopycatVerticalStepBlock extends CopycatBlock {

    public CopycatVerticalStepBlock(BlockBehaviour.Properties properties) {
        super(properties, VERTICAL_STEP, CopycatShape.VERTICAL_STEP);
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