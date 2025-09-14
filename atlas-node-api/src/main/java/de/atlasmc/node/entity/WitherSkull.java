package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface WitherSkull extends AcceleratingProjectile {
	
	public static final NBTSerializationHandler<WitherSkull>
	NBT_HANDLER = NBTSerializationHandler
					.builder(WitherSkull.class)
					.include(AcceleratingProjectile.NBT_HANDLER)
					.boolField("dangerous", WitherSkull::isCharged, WitherSkull::setCharged, false)
					.build();
	
	boolean isCharged();
	
	void setCharged(boolean charged);
	
	@Override
	default NBTSerializationHandler<? extends WitherSkull> getNBTHandler() {
		return NBT_HANDLER;
	}

}
