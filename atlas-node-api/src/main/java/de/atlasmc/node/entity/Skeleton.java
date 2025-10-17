package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Skeleton extends AbstractSkeleton {
	
	public static final NBTCodec<Skeleton>
	NBT_HANDLER = NBTCodec
					.builder(Skeleton.class)
					.include(AbstractSkeleton.NBT_HANDLER)
					.intField("StrayConversionTime", Skeleton::getStrayConversionTime, Skeleton::setStrayConversionTime, -1)
					.build();
	
	int getStrayConversionTime();
	
	void setStrayConversionTime(int time);
	
	@Override
	default NBTCodec<? extends Skeleton> getNBTCodec() {
		return NBT_HANDLER;
	}

}
