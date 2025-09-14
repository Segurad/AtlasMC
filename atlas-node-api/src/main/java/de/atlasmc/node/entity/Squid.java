package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Squid extends WaterAnimal, AgeableMob {

	public static final NBTSerializationHandler<Squid>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Squid.class)
					.include(WaterAnimal.NBT_HANDLER)
					.include(AgeableMob.NBT_HANDLER)
					.build();
	
}
