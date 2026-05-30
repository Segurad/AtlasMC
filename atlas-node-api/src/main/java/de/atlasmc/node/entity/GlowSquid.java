package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface GlowSquid extends Squid {
	
	@NotNull
	public static final NBTCodec<GlowSquid>
	NBT_CODEC = NBTCodec
					.builder(GlowSquid.class)
					.include(Squid.NBT_CODEC)
					.intField("DarkTicksRemaining", GlowSquid::getDarkTicksRemaining, GlowSquid::setDarkTicksRemaining, 0)
					.build();
	
	int getDarkTicksRemaining();
	
	void setDarkTicksRemaining(int ticks);
	
	@Override
	default NBTCodec<? extends GlowSquid> getNBTCodec() {
		return NBT_CODEC;
	}

}
