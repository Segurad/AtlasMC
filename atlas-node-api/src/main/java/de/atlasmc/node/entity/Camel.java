package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Camel extends AbstractHorse {
	
	public static final NBTCodec<Camel>
	NBT_HANDLER = NBTCodec
					.builder(Camel.class)
					.include(AbstractHorse.NBT_HANDLER)
					.longField("LastPoseTick", Camel::getLastPoseTick, Camel::setLastPoseTick)
					.build();
	
	long getLastPoseTick();
	
	void setLastPoseTick(long pose);
	
	boolean isDashing();
	
	void setDashing(boolean dashing);

	@Override
	default NBTCodec<? extends Camel> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
