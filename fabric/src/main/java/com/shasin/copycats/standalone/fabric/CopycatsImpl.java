package com.shasin.copycats.standalone.fabric;

import com.shasin.copycats.standalone.Copycats;
import com.shasin.copycats.standalone.api.CopycatAPI;
import com.shasin.copycats.standalone.block.CopycatBlock;
import com.shasin.copycats.standalone.block.CopycatBlockEntity;
import com.shasin.copycats.standalone.block.CopycatBlockEntityTypes;
import com.shasin.copycats.standalone.block.CopycatBlocks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.FabricBlockEntityType;
import net.minecraft.core.Registries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

/**
 * Fabric entry point. Registers blocks and the copycat block entity using native
 * Fabric API registration (no Registrate).
 */
public class CopycatsImpl implements ModInitializer {

    @Override
    public void onInitialize() {
        registerBlocks();
        registerBlockEntityType();
        Copycats.init();

        Copycats.LOGGER.info("Copycats (Fabric) initialized");
    }

    private void registerBlocks() {
        registerBlock(CopycatBlocks.COPYCAT_BLOCK, "copycat_block");
        registerBlock(CopycatBlocks.COPYCAT_SLAB, "copycat_slab");
        registerBlock(CopycatBlocks.COPYCAT_BYTE, "copycat_byte");
        registerBlock(CopycatBlocks.COPYCAT_STEP, "copycat_step");
        registerBlock(CopycatBlocks.COPYCAT_VERTICAL_STEP, "copycat_vertical_step");
    }

    @SuppressWarnings("unchecked")
    private static <B extends Block> void registerBlock(B block, String path) {
        Registry.register(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Copycats.MODID, path), block);
    }

    private void registerBlockEntityType() {
        BlockEntityType<CopycatBlockEntity> type = FabricBlockEntityType.Builder.create(CopycatBlockEntity::new)
                .build(null);
        Registry.register(Registries.BLOCK_ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(Copycats.MODID, "copycat"), type);
        CopycatBlockEntityTypes.setCopycatType(() -> type);

        // Example default mass provider: density scales with copycat volume.
        CopycatAPI.registerMassProvider(state -> {
            if (state.getBlock() instanceof CopycatBlock copycatBlock) {
                return copycatBlock.getVolumeRatio();
            }
            return null;
        });
    }
}