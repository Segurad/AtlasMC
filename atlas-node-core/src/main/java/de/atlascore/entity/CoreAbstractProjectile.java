package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.ProjectileSource;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Projectile;

public abstract class CoreAbstractProjectile extends CoreEntity implements Projectile {

	private ProjectileSource source;
	private boolean bounce;
	
	public CoreAbstractProjectile(EntityType type, UUID uuid) {
		super(type, uuid);
	}

	@Override
	public ProjectileSource getShooter() {
		return source;
	}

	@Override
	public boolean doesBounce() {
		return bounce;
	}

	@Override
	public void setShooter(ProjectileSource source) {
		this.source = source;
	}

	@Override
	public void setBounce(boolean bounce) {
		this.bounce = bounce;
	}

}
