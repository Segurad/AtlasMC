package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface PufferFish extends Fish {

	public static final NBTSerializationHandler<PufferFish>
	NBT_HANDLER = NBTSerializationHandler
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
