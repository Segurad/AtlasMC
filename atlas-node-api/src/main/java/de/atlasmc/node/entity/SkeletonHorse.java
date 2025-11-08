package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;

public interface SkeletonHorse extends AbstractHorse {

	public static final NBTCodec<SkeletonHorse>
	NBT_HANDLER = NBTCodec
					.builder(SkeletonHorse.class)
					.include(AbstractHorse.NBT_HANDLER)
					.boolField("SkeletonTrap", SkeletonHorse::isSkeletonTrap, SkeletonHorse::setSkeletonTrap, false)
					.intField("SkeletonTrapTime", SkeletonHorse::getSkeletonTrapTime, SkeletonHorse::setSkeletonTrapTime, 0)
					.build();
	
	boolean isSkeletonTrap();
	
	void setSkeletonTrap(boolean trap);
	
	int getSkeletonTrapTime();
	
	void setSkeletonTrapTime(int time);
	
	@Override
	default NBTCodec<? extends SkeletonHorse> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
