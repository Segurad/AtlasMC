package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface Camel extends AbstractHorse {
	
	@NotNull
	public static final NBTCodec<Camel>
	NBT_HANDLER = NBTCodec
					.builder(Camel.class)
					.include(AbstractHorse.NBT_CODEC)
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
