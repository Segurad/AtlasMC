package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface GlowSquid extends Squid {
	
	public static final NBTCodec<GlowSquid>
	NBT_HANDLER = NBTCodec
					.builder(GlowSquid.class)
					.include(Squid.NBT_HANDLER)
					.intField("DarkTicksRemaining", GlowSquid::getDarkTicksRemaining, GlowSquid::setDarkTicksRemaining, 0)
					.build();
	
	int getDarkTicksRemaining();
	
	void setDarkTicksRemaining(int ticks);
	
	@Override
	default NBTCodec<? extends GlowSquid> getNBTCodec() {
		return NBT_HANDLER;
	}

}
