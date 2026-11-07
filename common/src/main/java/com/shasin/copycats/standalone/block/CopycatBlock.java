package com.shasin.copycats.standalone.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;

/**
 * Base copycat block. Every copycat block has a block entity (so it can store the
 * copied material and computed mass) and a fixed collision/render shape.
 */
public abstract class CopycatBlock extends Block {

    protected final VoxelShape shape;
    protected final CopycatShape shapeType;

    protected CopycatBlock(BlockBehaviour.Properties properties, VoxelShape shape, CopycatShape shapeType) {
        super(properties);
        this.shape = shape;
        this.shapeType = shapeType;
    }

    public VoxelShape getShape(BlockState state, LevelReader level, BlockPos pos) {
        return shape;
    }

    public VoxelShape getCollisionShape(BlockState state, LevelReader level, BlockPos pos) {
        return shape;
    }

    public boolean hasBlockEntity(BlockState state) {
        return true;
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        CopycatBlockEntity blockEntity = new CopycatBlockEntity(pos, state);
        blockEntity.setVolumeRatio(getVolumeRatio());
        return blockEntity;
    }

    /**
     * Volume ratio used for mass calculations.
     * <ul>
     *   <li>Slab = 0.5f</li>
     *   <li>Byte = 0.125f</li>
     *   <li>Step / VerticalStep / Full = 1.0f</li>
     * </ul>
     */
    public float getVolumeRatio() {
        return 1.0f;
    }

    public CopycatShape getShapeType() {
        return shapeType;
    }

    /** Full block shape (a complete cube). */
    public static final VoxelShape FULL = Shapes.block();

    /** Slab shape (bottom half of a block). */
    public static final VoxelShape SLAB = Shapes.box(0, 0, 0, 1, 0.5, 1);

    /** Step shape (half depth). */
    public static final VoxelShape STEP = Shapes.box(0, 0, 0, 1, 1, 0.5);

    /** Vertical step shape (half width). */
    public static final VoxelShape VERTICAL_STEP = Shapes.box(0, 0, 0, 0.5, 1, 1);

    /** Byte shape (one eighth of a block). */
    public static final VoxelShape BYTE = Shapes.box(0, 0, 0, 0.5, 0.5, 0.5);
}