package de.atlasmc.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Endermite extends Monster {

	public static final NBTSerializationHandler<Endermite>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Endermite.class)
					.include(Monster.NBT_HANDLER)
					.intField("Lifetime", Endermite::getLifetime, Endermite::setLifetime, -1)
					.build();
	
	/**
	 * Sets the time in tick this Endermite will be alive or -1 for infinite time
	 * @param lifetime
	 */
	void setLifetime(int lifetime);

	/**
	 * Returns the time in ticks this Endermite will be alive.<br>
	 * Will be -1 for infinite time
	 * @return ticks or -1
	 */
	int getLifetime();
	
}
