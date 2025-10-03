package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ZombifiedPiglin extends Zombie, AngerableMob  {
	
	public static final NBTSerializationHandler<ZombifiedPiglin>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ZombifiedPiglin.class)
					.include(Zombie.NBT_HANDLER)
					.include(AngerableMob.NBT_HANDLER)
					.build();
	
	@Override
	default NBTSerializationHandler<? extends ZombifiedPiglin> getNBTHandler() {
		return NBT_HANDLER;
	}

}
