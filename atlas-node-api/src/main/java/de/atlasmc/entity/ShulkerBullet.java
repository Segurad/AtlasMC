package de.atlasmc.entity;

import java.util.UUID;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ShulkerBullet extends Projectile {
	
	public static final NBTSerializationHandler<ShulkerBullet>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ShulkerBullet.class)
					.include(Projectile.NBT_HANDLER)
					//.intField("Steps", null, null)
					.uuid("Target", ShulkerBullet::getTargetUUID, ShulkerBullet::setTargetUUID)
					//.doubleField("TXD", null, null)
					//.doubleField("TYD", null, null)
					//.doubleField("TZD", null, null)
					.build();
	
	void setTarget(Entity target);
	
	Entity getTarget();

	void setTargetUUID(UUID uuid);
	
	UUID getTargetUUID();
	
	@Override
	default NBTSerializationHandler<? extends ShulkerBullet> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
