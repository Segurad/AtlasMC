package de.atlasmc.node.entity;

import java.util.UUID;

import de.atlasmc.node.ProjectileSource;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Projectile extends Entity {
	
	public static final NBTSerializationHandler<Projectile>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Projectile.class)
					.include(Entity.NBT_HANDLER)
					// ignore because event will be triggered by shooter 
					//.boolField("HasBeenShot", Projectile::hasBeenShot, Projectile::setBeenShoot, true)
					.boolField("LeftOwner", Projectile::hasLeftOwner, Projectile::setLeftOwner, false)
					.uuid("Owner", Projectile::getShooterUUID, Projectile::setShooterUUID)
					.build();
	
	ProjectileSource getShooter();
	
	void setShooter(ProjectileSource source);
	
	UUID getShooterUUID();
	
	void setShooterUUID(UUID uuid);
	
	boolean hasLeftOwner();
	
	void setLeftOwner(boolean value);
	
	@Override
	default NBTSerializationHandler<? extends Projectile> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
