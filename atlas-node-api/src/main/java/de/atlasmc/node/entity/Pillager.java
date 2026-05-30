package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.PocketHolder;
import de.atlasmc.util.annotation.NotNull;

public interface Pillager extends AbstractIllager, PocketHolder {
	
	@NotNull
	public static final NBTCodec<Pillager>
	NBT_CODEC = NBTCodec
					.builder(Pillager.class)
					.include(AbstractIllager.NBT_CODEC)
					.include(PocketHolder.NBT_CODEC)
					.boolField("IsCharging", Pillager::isCharging, Pillager::setCharging, false) // non standard
					.build();
	
	boolean isCharging();
	
	void setCharging(boolean charging);
	
	@Override
	default NBTCodec<? extends Pillager> getNBTCodec() {
		return NBT_CODEC;
	}

}
