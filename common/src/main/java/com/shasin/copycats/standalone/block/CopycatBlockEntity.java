package com.shasin.copycats.standalone.block;

import com.shasin.copycats.standalone.api.CopycatAPI;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Base copycat block entity. Replaces the old Create-backed SmartBlockEntity with a
 * plain vanilla {@link BlockEntity}.
 * <p>
 * Stores the copied {@link #material} and computes a final mass via the decoupled
 * {@link CopycatAPI} SPI scaled by the copycat volume ratio.
 */
public class CopycatBlockEntity extends BlockEntity {

    protected BlockState material;
    protected boolean connectedTexture;
    protected float volumeRatio = 1.0f;

    public CopycatBlockEntity(BlockPos pos, BlockState state) {
        super(CopycatBlockEntityTypes.getCopycatType(), pos, state);
    }

    public BlockState getMaterial() {
        return material;
    }

    public void setMaterial(BlockState material) {
        this.material = material;
    }

    public boolean isConnectedTexture() {
        return connectedTexture;
    }

    public void setConnectedTexture(boolean connectedTexture) {
        this.connectedTexture = connectedTexture;
    }

    public float getVolumeRatio() {
        return volumeRatio;
    }

    public void setVolumeRatio(float volumeRatio) {
        this.volumeRatio = volumeRatio;
    }

    /**
     * Compute the final mass: {@code CopycatAPI.getMaterialMass(material) * volumeRatio}.
     * Safely returns the fallback density scaled by volume when the material is null or AIR.
     */
    public float getMass() {
        if (material == null || material.isAir()) {
            return 1.0f * volumeRatio;
        }
        return CopycatAPI.getMaterialMass(material) * volumeRatio;
    }
}