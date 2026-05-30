package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface SizedFireball extends AcceleratingProjectile, ThrowableProjectile {

	@NotNull
	public static final NBTCodec<SizedFireball>
	NBT_CODEC = NBTCodec
					.builder(SizedFireball.class)
					.include(AcceleratingProjectile.NBT_CODEC)
					.include(ThrowableProjectile.NBT_CODEC)
					.build();
	
	@Override
	default NBTCodec<? extends SizedFireball> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
