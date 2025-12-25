package de.atlasmc.node.world.particle;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.block.data.BlockData;

public class BlockDataParticle extends AbstractParticle {

	public static final NBTCodec<BlockDataParticle>
	NBT_CODEC = NBTCodec
				.builder(BlockDataParticle.class)
				.include(AbstractParticle.NBT_CODEC)
				.codec("block_state", BlockDataParticle::getData, BlockDataParticle::setData, BlockData.NBT_CODEC)
				.build();
	
	public static final StreamCodec<BlockDataParticle>
	STREAM_CODEC = StreamCodec
					.builder(BlockDataParticle.class)
					.include(AbstractParticle.STREAM_CODEC)
					.varInt(BlockDataParticle::getStateID, (_, _) -> {}) // TODO state id to block data
					.build();
	
	public BlockData data;
	
	public BlockDataParticle(ParticleType type) {
		super(type);
	}
	
	public BlockData getData() {
		return data;
	}
	
	public void setData(BlockData data) {
		this.data = data;
	}
	
	public int getStateID() {
		return data == null ? 0 : data.getStateID();
	}

	@Override
	public BlockDataParticle clone() {
		BlockDataParticle clone = (BlockDataParticle) super.clone();
		if (data != null)
			clone.data = data.clone();
		return clone;
	}
	
	@Override
	public NBTCodec<? extends BlockDataParticle> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	public StreamCodec<? extends BlockDataParticle> getStreamCodec() {
		return STREAM_CODEC;
	}

}
