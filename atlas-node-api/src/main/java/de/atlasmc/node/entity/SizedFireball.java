package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface SizedFireball extends AcceleratingProjectile, ThrowableProjectile {

	public static final NBTCodec<SizedFireball>
	NBT_HANDLER = NBTCodec
					.builder(SizedFireball.class)
					.include(AcceleratingProjectile.NBT_HANDLER)
					.include(ThrowableProjectile.NBT_HANDLER)
					.build();
	
	@Override
	default NBTCodec<? extends SizedFireball> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
