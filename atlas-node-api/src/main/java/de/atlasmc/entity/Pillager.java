package de.atlasmc.entity;

import de.atlasmc.inventory.PocketHolder;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Pillager extends AbstractIllager, PocketHolder {
	
	public static final NBTSerializationHandler<Pillager>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Pillager.class)
					.include(AbstractIllager.NBT_HANDLER)
					.include(PocketHolder.NBT_HANDLER)
					.boolField("IsCharging", Pillager::isCharging, Pillager::setCharging, false) // non standard
					.build();
	
	boolean isCharging();
	
	void setCharging(boolean charging);

}
