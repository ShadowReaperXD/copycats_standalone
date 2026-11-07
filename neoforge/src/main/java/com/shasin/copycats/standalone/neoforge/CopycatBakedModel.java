package com.shasin.copycats.standalone.neoforge;

import com.shasin.copycats.standalone.block.CopycatShape;
import com.shasin.copycats.standalone.client.CopycatQuadData;
import com.shasin.copycats.standalone.client.CopycatQuadTransformer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.ModelOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import java.util.List;
import java.util.stream.Collectors;

/**
 * NeoForge baked model for copycat blocks. Converts generic {@link CopycatQuadData}
 * into {@link BakedQuad} instances via {@link CopycatQuadTransformer}.
 */
public class CopycatBakedModel implements BakedModel {

    private final CopycatShape shape;

    public CopycatBakedModel(CopycatShape shape) {
        this.shape = shape;
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction face, RandomSource random) {
        List<CopycatQuadData> quadData = CopycatQuadTransformer.buildQuads(state, shape, 0);
        return quadData.stream()
                .map(qd -> new BakedQuad(qd.vertices(), qd.tintIndex(),
                        Direction.values()[qd.faceOrdinal()], qd.shade()))
                .collect(Collectors.toList());
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
}