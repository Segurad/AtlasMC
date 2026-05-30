package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface WitherSkull extends AcceleratingProjectile {
	
	@NotNull
	public static final NBTCodec<WitherSkull>
	NBT_CODEC = NBTCodec
					.builder(WitherSkull.class)
					.include(AcceleratingProjectile.NBT_CODEC)
					.boolField("dangerous", WitherSkull::isCharged, WitherSkull::setCharged, false)
					.build();
	
	boolean isCharged();
	
	void setCharged(boolean charged);
	
	@Override
	default NBTCodec<? extends WitherSkull> getNBTCodec() {
		return NBT_CODEC;
	}

}
