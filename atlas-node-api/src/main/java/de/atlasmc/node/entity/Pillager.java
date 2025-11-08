package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.PocketHolder;

public interface Pillager extends AbstractIllager, PocketHolder {
	
	public static final NBTCodec<Pillager>
	NBT_HANDLER = NBTCodec
					.builder(Pillager.class)
					.include(AbstractIllager.NBT_HANDLER)
					.include(PocketHolder.NBT_HANDLER)
					.boolField("IsCharging", Pillager::isCharging, Pillager::setCharging, false) // non standard
					.build();
	
	boolean isCharging();
	
	void setCharging(boolean charging);

}
