package de.atlasmc.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface GlowSquid extends Squid {
	
	public static final NBTSerializationHandler<GlowSquid>
	NBT_HANDLER = NBTSerializationHandler
					.builder(GlowSquid.class)
					.include(Squid.NBT_HANDLER)
					.intField("DarkTicksRemaining", GlowSquid::getDarkTicksRemaining, GlowSquid::setDarkTicksRemaining, 0)
					.build();
	
	int getDarkTicksRemaining();
	
	void setDarkTicksRemaining(int ticks);
	
	@Override
	default NBTSerializationHandler<? extends GlowSquid> getNBTHandler() {
		return NBT_HANDLER;
	}

}
