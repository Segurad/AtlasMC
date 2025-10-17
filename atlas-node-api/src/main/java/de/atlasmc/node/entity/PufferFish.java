package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface PufferFish extends Fish {

	public static final NBTCodec<PufferFish>
	NBT_HANDLER = NBTCodec
					.builder(PufferFish.class)
					.include(Fish.NBT_HANDLER)
					.intField("PuffState", PufferFish::getPuffState, PufferFish::setPuffState, 0)
					.build();
	
	int getPuffState();
	
	/**
	 * Sets the puff state between 0 and 2
	 * @param state
	 */
	void setPuffState(int state);

}
