package de.atlasmc.node.world.positionsource;

import org.joml.Vector3d;
import org.joml.Vector3i;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.node.util.MathUtil;
import de.atlasmc.node.world.World;

public class BlockPositionSource implements PositionSource {

	public static final NBTCodec<BlockPositionSource>
	NBT_CODEC = NBTCodec
				.builder(BlockPositionSource.class)
				.include(PositionSource.NBT_CODEC)
				.codec("pos", BlockPositionSource::getPosition, BlockPositionSource::setPosition, NBTCodecs.VECTOR_3I)
				.build();
	
	public static final StreamCodec<BlockPositionSource>
	STREAM_CODEC = StreamCodec
					.builder(BlockPositionSource.class)
					.include(PositionSource.STREAM_CODEC)
					.longValue(BlockPositionSource::getPositionValue, BlockPositionSource::setPosition)
					.build();
	
	private Vector3i position;
	
	public BlockPositionSource(Vector3i vec) {
		this.position = vec;
	}
	
	public BlockPositionSource() {
		position = new Vector3i();
	}
	
	@Override
	public Vector3d getPosition(World world, Vector3d vec) {
		return vec.set(position);
	}
	
	public long getPositionValue() {
		return MathUtil.toPosition(position);
	}
	
	public Vector3i getPosition() {
		return position;
	}
	
	public void setPosition(Vector3i vec) {
		position.set(vec);
	}
	
	public void setPosition(long pos) {
		MathUtil.getPositionVector(position, pos);
	}

	@Override
	public PositionSourceType getType() {
		return PositionSourceType.BLOCK;
	}
	
	@Override
	public NBTCodec<? extends BlockPositionSource> getNBTCodec() {
		return NBT_CODEC;
	}

	@Override
	public StreamCodec<? extends BlockPositionSource> getStreamCodec() {
		return STREAM_CODEC;
	}

}
