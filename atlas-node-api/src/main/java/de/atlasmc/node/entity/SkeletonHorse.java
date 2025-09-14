package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface SkeletonHorse extends AbstractHorse {

	public static final NBTSerializationHandler<SkeletonHorse>
	NBT_HANDLER = NBTSerializationHandler
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
	default NBTSerializationHandler<? extends SkeletonHorse> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
