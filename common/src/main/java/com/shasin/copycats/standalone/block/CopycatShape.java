package com.shasin.copycats.standalone.block;

/**
 * Copycat shape ordinals. Used as part of the quad-cache key so that each shape
 * gets its own cached quad set without rebuilding geometry.
 */
public enum CopycatShape {
    FULL,
    SLAB,
    STEP,
    BYTE,
    VERTICAL_STEP
}