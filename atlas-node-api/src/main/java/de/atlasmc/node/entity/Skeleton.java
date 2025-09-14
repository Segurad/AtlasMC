package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Skeleton extends AbstractSkeleton {
	
	public static final NBTSerializationHandler<Skeleton>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Skeleton.class)
					.include(AbstractSkeleton.NBT_HANDLER)
					.intField("StrayConversionTime", Skeleton::getStrayConversionTime, Skeleton::setStrayConversionTime, -1)
					.build();
	
	int getStrayConversionTime();
	
	void setStrayConversionTime(int time);
	
	@Override
	default NBTSerializationHandler<? extends Skeleton> getNBTHandler() {
		return NBT_HANDLER;
	}

}
