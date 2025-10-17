package de.atlasmc.node.entity;

import java.util.UUID;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface ShulkerBullet extends Projectile {
	
	public static final NBTCodec<ShulkerBullet>
	NBT_HANDLER = NBTCodec
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
	default NBTCodec<? extends ShulkerBullet> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
