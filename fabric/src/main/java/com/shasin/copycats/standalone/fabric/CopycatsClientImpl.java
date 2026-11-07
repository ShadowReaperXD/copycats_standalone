package com.shasin.copycats.standalone.fabric;

import com.shasin.copycats.standalone.Copycats;
import com.shasin.copycats.standalone.block.CopycatShape;
import com.shasin.copycats.standalone.client.CopycatQuadData;
import com.shasin.copycats.standalone.client.CopycatQuadTransformer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.minecraft.client.renderer.BlockRenderView;
import net.minecraft.client.renderer.RenderContext;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.ItemTransforms;
import net.minecraft.client.renderer.model.ModelOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import java.util.List;

public class CopycatsClientImpl implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Copycats.LOGGER.info("Copycats client (Fabric) initialized");
    }

    public static class FabricCopycatBakedModel implements FabricBakedModel {

        private final CopycatShape shape;

        public FabricCopycatBakedModel(CopycatShape shape) {
            this.shape = shape;
        }

        @Override
        public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos,
                                   java.util.function.Supplier<RandomSource> randomSupplier,
                                   RenderContext context) {
            int mask = CopycatQuadTransformer.computeConnectionMask(blockView, pos, state);
            List<CopycatQuadData> quadData = CopycatQuadTransformer.buildQuads(state, shape, mask);
            for (CopycatQuadData qd : quadData) {
                context.emit(toBakedQuad(qd));
            }
        }

        @Override
        public void emitItemQuads(ItemStack stack, java.util.function.Supplier<RandomSource> randomSupplier,
                                  RenderContext context) {
            List<CopycatQuadData> quadData = CopycatQuadTransformer.buildQuads(null, shape, 0);
            for (CopycatQuadData qd : quadData) {
                context.emit(toBakedQuad(qd));
            }
        }

        @Override
        public boolean isVanillaAdapter() {
            return false;
        }

        @Override
        public List<BakedQuad> getQuads(BlockState state, Direction face, RandomSource random) {
            return List.of();
        }

        @Override
        public boolean useAmbientOcclusion() {
            return true;
        }

        @Override
        public boolean hasDepth() {
            return true;
        }

        @Override
        public boolean isBuiltInRenderer() {
            return false;
        }

        @Override
        public ItemTransforms getTransforms() {
            return ItemTransforms.NO_TRANSFORMS;
        }

        @Override
        public ModelOverrides getOverrides() {
            return ModelOverrides.EMPTY;
        }

        @Override
        public TextureAtlasSprite getParticleIcon() {
            return null;
        }

        private static BakedQuad toBakedQuad(CopycatQuadData qd) {
            return new BakedQuad(qd.vertices(), qd.tintIndex(), Direction.values()[qd.faceOrdinal()], qd.shade());
        }
    }
}