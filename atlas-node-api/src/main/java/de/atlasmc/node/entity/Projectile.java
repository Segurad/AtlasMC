package de.atlasmc.node.entity;

import java.util.UUID;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.node.ProjectileSource;

public interface Projectile extends Entity {
	
	public static final NBTCodec<Projectile>
	NBT_HANDLER = NBTCodec
					.builder(Projectile.class)
					.include(Entity.NBT_CODEC)
					// ignore because event will be triggered by shooter 
					//.boolField("HasBeenShot", Projectile::hasBeenShot, Projectile::setBeenShoot, true)
					.boolField("LeftOwner", Projectile::hasLeftOwner, Projectile::setLeftOwner, false)
					.codec("Owner", Projectile::getShooterUUID, Projectile::setShooterUUID, NBTCodecs.UUID_CODEC)
					.build();
	
	ProjectileSource getShooter();
	
	void setShooter(ProjectileSource source);
	
	UUID getShooterUUID();
	
	void setShooterUUID(UUID uuid);
	
	boolean hasLeftOwner();
	
	void setLeftOwner(boolean value);
	
	@Override
	default NBTCodec<? extends Projectile> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
