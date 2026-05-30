package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface SkeletonHorse extends AbstractHorse {

	@NotNull
	public static final NBTCodec<SkeletonHorse>
	NBT_CODEC = NBTCodec
					.builder(SkeletonHorse.class)
					.include(AbstractHorse.NBT_CODEC)
					.boolField("SkeletonTrap", SkeletonHorse::isSkeletonTrap, SkeletonHorse::setSkeletonTrap, false)
					.intField("SkeletonTrapTime", SkeletonHorse::getSkeletonTrapTime, SkeletonHorse::setSkeletonTrapTime, 0)
					.build();
	
	boolean isSkeletonTrap();
	
	void setSkeletonTrap(boolean trap);
	
	int getSkeletonTrapTime();
	
	void setSkeletonTrapTime(int time);
	
	@Override
	default NBTCodec<? extends SkeletonHorse> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
