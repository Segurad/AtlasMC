package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface SizedFireball extends AcceleratingProjectile, ThrowableProjectile {

	public static final NBTSerializationHandler<SizedFireball>
	NBT_HANDLER = NBTSerializationHandler
					.builder(SizedFireball.class)
					.include(AcceleratingProjectile.NBT_HANDLER)
					.include(ThrowableProjectile.NBT_HANDLER)
					.build();
	
	@Override
	default NBTSerializationHandler<? extends SizedFireball> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
