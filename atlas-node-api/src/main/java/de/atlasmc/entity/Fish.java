package de.atlasmc.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Fish extends WaterAnimal {
	
	public static final NBTSerializationHandler<Fish>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Fish.class)
					.include(WaterAnimal.NBT_HANDLER)
					.boolField("FromBucket", Fish::isFromBucket, Fish::setFromBucket, false)
					.build();
	
	boolean isFromBucket();
	
	void setFromBucket(boolean from);
	
	@Override
	default NBTSerializationHandler<? extends Fish> getNBTHandler() {
		return NBT_HANDLER;
	}

}
