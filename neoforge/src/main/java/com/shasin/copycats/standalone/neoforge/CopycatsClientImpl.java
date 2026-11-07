package com.shasin.copycats.standalone.neoforge;

import com.shasin.copycats.standalone.Copycats;
import com.shasin.copycats.standalone.block.CopycatShape;
import net.minecraft.client.renderer.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ModelEvent;
import java.util.function.BiFunction;

@Mod(value = Copycats.MODID, dist = Dist.CLIENT)
public class CopycatsClientImpl {

    private static final BiFunction<ResourceLocation, BakedModel, BakedModel>[] REPLACEMENTS = new BiFunction[]{
        (key, model) -> new CopycatBakedModel(CopycatShape.FULL),
        (key, model) -> new CopycatBakedModel(CopycatShape.SLAB),
        (key, model) -> new CopycatBakedModel(CopycatShape.BYTE),
        (key, model) -> new CopycatBakedModel(CopycatShape.STEP),
        (key, model) -> new CopycatBakedModel(CopycatShape.VERTICAL_STEP)
    };

    private static final ResourceLocation[] PATHS = {
        Copycats.asResource("copycat_block"),
        Copycats.asResource("copycat_slab"),
        Copycats.asResource("copycat_byte"),
        Copycats.asResource("copycat_step"),
        Copycats.asResource("copycat_vertical_step")
    };

    public CopycatsClientImpl(IEventBus modBus) {
        modBus.addListener(EventPriority.HIGHEST, this::onModifyBakingResult);
        Copycats.LOGGER.info("Copycats client (NeoForge) initialized");
    }

    private void onModifyBakingResult(ModelEvent.ModifyBakingResult event) {
        for (int i = 0; i < PATHS.length; i++) {
            event.getModels().computeIfPresent(PATHS[i], REPLACEMENTS[i]);
        }
    }
}