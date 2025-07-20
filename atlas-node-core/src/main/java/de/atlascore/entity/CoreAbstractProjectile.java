package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.ProjectileSource;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Projectile;

public abstract class CoreAbstractProjectile extends CoreEntity implements Projectile {

	private ProjectileSource source;
	private UUID ownerID;
	private boolean leftOwner;
	
	public CoreAbstractProjectile(EntityType type) {
		super(type);
	}

	@Override
	public ProjectileSource getShooter() {
		return source;
	}

	@Override
	public void setShooter(ProjectileSource source) {
		this.source = source;
	}
	
	@Override
	public UUID getShooterUUID() {
		return ownerID;
	}
	
	@Override
	public void setShooterUUID(UUID uuid) {
		this.ownerID = uuid;
	}
	
	@Override
	public boolean hasLeftOwner() {
		return leftOwner;
	}
	
	@Override
	public void setLeftOwner(boolean value) {
		this.leftOwner = value;
	}
	
}
