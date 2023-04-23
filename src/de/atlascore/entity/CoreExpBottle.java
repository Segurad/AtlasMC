package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.ExpBottle;

public class CoreExpBottle extends CoreThrowableProjectile implements ExpBottle {

	public CoreExpBottle(EntityType type, UUID uuid) {
		super(type, uuid);
	}

	@Override
	public ProjectileType getProjectileType() {
		return ProjectileType.EXP_BOTTLE;
	}

}
