package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface Fish extends WaterAnimal {
	
	@NotNull
	public static final NBTCodec<Fish>
	NBT_CODEC = NBTCodec
					.builder(Fish.class)
					.include(WaterAnimal.NBT_CODEC)
					.boolField("FromBucket", Fish::isFromBucket, Fish::setFromBucket, false)
					.build();
	
	boolean isFromBucket();
	
	void setFromBucket(boolean from);
	
	@Override
	default NBTCodec<? extends Fish> getNBTCodec() {
		return NBT_CODEC;
	}

}
