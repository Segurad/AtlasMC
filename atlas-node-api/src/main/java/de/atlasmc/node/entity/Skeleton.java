package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface Skeleton extends AbstractSkeleton {
	
	@NotNull
	public static final NBTCodec<Skeleton>
	NBT_CODEC = NBTCodec
					.builder(Skeleton.class)
					.include(AbstractSkeleton.NBT_CODEC)
					.intField("StrayConversionTime", Skeleton::getStrayConversionTime, Skeleton::setStrayConversionTime, -1)
					.build();
	
	int getStrayConversionTime();
	
	void setStrayConversionTime(int time);
	
	@Override
	default NBTCodec<? extends Skeleton> getNBTCodec() {
		return NBT_CODEC;
	}

}
