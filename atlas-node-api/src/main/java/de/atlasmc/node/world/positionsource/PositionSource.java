package de.atlasmc.node.world.positionsource;

import org.joml.Vector3d;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.io.codec.StreamSerializable;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.node.world.World;

public interface PositionSource extends NBTSerializable, StreamSerializable {
	
	public static final NBTCodec<PositionSource>
	NBT_CODEC = NBTCodec
				.builder(PositionSource.class)
				.searchKeyEnumConstructor("type", PositionSourceType.class, PositionSourceType::createSource, PositionSource::getType)
				.build();
	
	public static final StreamCodec<PositionSource>
	STREAM_CODEC = StreamCodec
					.builder(PositionSource.class)
					.enumVarIntConstructor(PositionSourceType.class, PositionSourceType::createSource, PositionSource::getType)
					.build();
	
	default Vector3d getPosition(World world) {
		return getPosition(world, new Vector3d());
	}
	
	Vector3d getPosition(World world, Vector3d vec);
	
	PositionSourceType getType();
	
	@Override
	default NBTCodec<? extends PositionSource> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends PositionSource> getStreamCodec() {
		return STREAM_CODEC;
	}

}
