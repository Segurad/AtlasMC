package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Camel extends AbstractHorse {
	
	public static final NBTSerializationHandler<Camel>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Camel.class)
					.include(AbstractHorse.NBT_HANDLER)
					.longField("LastPoseTick", Camel::getLastPoseTick, Camel::setLastPoseTick)
					.build();
	
	long getLastPoseTick();
	
	void setLastPoseTick(long pose);
	
	boolean isDashing();
	
	void setDashing(boolean dashing);

	@Override
	default NBTSerializationHandler<? extends Camel> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
