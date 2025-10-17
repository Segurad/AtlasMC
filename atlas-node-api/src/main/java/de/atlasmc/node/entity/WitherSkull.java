package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface WitherSkull extends AcceleratingProjectile {
	
	public static final NBTCodec<WitherSkull>
	NBT_HANDLER = NBTCodec
					.builder(WitherSkull.class)
					.include(AcceleratingProjectile.NBT_HANDLER)
					.boolField("dangerous", WitherSkull::isCharged, WitherSkull::setCharged, false)
					.build();
	
	boolean isCharged();
	
	void setCharged(boolean charged);
	
	@Override
	default NBTCodec<? extends WitherSkull> getNBTCodec() {
		return NBT_HANDLER;
	}

}
