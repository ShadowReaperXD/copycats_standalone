package com.shasin.copycats.standalone.neoforge;

import com.shasin.copycats.standalone.Copycats;
import com.shasin.copycats.standalone.api.CopycatAPI;
import com.shasin.copycats.standalone.block.CopycatBlock;
import com.shasin.copycats.standalone.block.CopycatBlockEntity;
import com.shasin.copycats.standalone.block.CopycatBlockEntityTypes;
import com.shasin.copycats.standalone.block.CopycatBlocks;
import net.minecraft.core.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

/**
 * NeoForge entry point. Registers blocks and the copycat block entity using
 * native NeoForge {@link DeferredRegister} (no Registrate).
 */
@Mod(Copycats.MODID)
public class CopycatsImpl {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, Copycats.MODID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Copycats.MODID);

    public static final DeferredBlock<Block> COPYCAT_BLOCK =
            BLOCKS.register("copycat_block", () -> CopycatBlocks.COPYCAT_BLOCK);
    public static final DeferredBlock<Block> COPYCAT_SLAB =
            BLOCKS.register("copycat_slab", () -> CopycatBlocks.COPYCAT_SLAB);
    public static final DeferredBlock<Block> COPYCAT_BYTE =
            BLOCKS.register("copycat_byte", () -> CopycatBlocks.COPYCAT_BYTE);
    public static final DeferredBlock<Block> COPYCAT_STEP =
            BLOCKS.register("copycat_step", () -> CopycatBlocks.COPYCAT_STEP);
    public static final DeferredBlock<Block> COPYCAT_VERTICAL_STEP =
            BLOCKS.register("copycat_vertical_step", () -> CopycatBlocks.COPYCAT_VERTICAL_STEP);

    private static final Supplier<BlockEntityType<?>> COPYCAT_BLOCK_ENTITY_TYPE =
            BLOCK_ENTITY_TYPES.register("copycat", () -> BlockEntityType.Builder.of(
                            CopycatBlockEntity::new,
                            CopycatBlocks.COPYCAT_BLOCK,
                            CopycatBlocks.COPYCAT_SLAB,
                            CopycatBlocks.COPYCAT_BYTE,
                            CopycatBlocks.COPYCAT_STEP,
                            CopycatBlocks.COPYCAT_VERTICAL_STEP)
                    .build(null));

    public CopycatsImpl(IEventBus modBus, ModContainer container) {
        BLOCKS.register(modBus);
        BLOCK_ENTITY_TYPES.register(modBus);

        // Resolve lazily: block entities are instantiated at runtime, after registration.
        CopycatBlockEntityTypes.setCopycatType(
                () -> (BlockEntityType<CopycatBlockEntity>) COPYCAT_BLOCK_ENTITY_TYPE.get());

        // Default mass provider: density scales with copycat volume ratio.
        CopycatAPI.registerMassProvider(state -> {
            if (state.getBlock() instanceof CopycatBlock copycatBlock) {
                return copycatBlock.getVolumeRatio();
            }
            return null;
        });

        Copycats.LOGGER.info("Copycats (NeoForge) initialized");
    }
}