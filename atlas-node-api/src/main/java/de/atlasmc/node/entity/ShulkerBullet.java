package de.atlasmc.node.entity;

import java.util.UUID;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.util.annotation.NotNull;

public interface ShulkerBullet extends Projectile {
	
	@NotNull
	public static final NBTCodec<ShulkerBullet>
	NBT_CODEC = NBTCodec
					.builder(ShulkerBullet.class)
					.include(Projectile.NBT_CODEC)
					//.intField("Steps", null, null)
					.codec("Target", ShulkerBullet::getTargetUUID, ShulkerBullet::setTargetUUID, NBTCodecs.UUID_CODEC)
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
		return NBT_CODEC;
	}
	
}
