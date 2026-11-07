package com.shasin.copycats.standalone.block;

import net.minecraft.world.level.block.entity.BlockEntityType;
import java.util.function.Supplier;

/**
 * Holds the registered copycat block entity type. Populated by the platform
 * module during registration.
 */
public final class CopycatBlockEntityTypes {
    private static Supplier<BlockEntityType<CopycatBlockEntity>> COPYCAT_TYPE;

    private CopycatBlockEntityTypes() {
    }

    public static void setCopycatType(Supplier<BlockEntityType<CopycatBlockEntity>> type) {
        COPYCAT_TYPE = type;
    }

    public static BlockEntityType<CopycatBlockEntity> getCopycatType() {
        if (COPYCAT_TYPE == null) {
            throw new IllegalStateException("Copycat block entity type has not been registered yet");
        }
        return COPYCAT_TYPE.get();
    }
}