package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Fish extends WaterAnimal {
	
	public static final NBTCodec<Fish>
	NBT_HANDLER = NBTCodec
					.builder(Fish.class)
					.include(WaterAnimal.NBT_HANDLER)
					.boolField("FromBucket", Fish::isFromBucket, Fish::setFromBucket, false)
					.build();
	
	boolean isFromBucket();
	
	void setFromBucket(boolean from);
	
	@Override
	default NBTCodec<? extends Fish> getNBTCodec() {
		return NBT_HANDLER;
	}

}
