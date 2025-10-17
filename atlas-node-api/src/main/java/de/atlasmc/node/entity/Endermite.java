package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Endermite extends Monster {

	public static final NBTCodec<Endermite>
	NBT_HANDLER = NBTCodec
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
