package de.atlasmc.entity;

import de.atlasmc.entity.data.AngerableMob;
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
