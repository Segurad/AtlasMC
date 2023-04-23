package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.EyeOfEnder;

public class CoreEyeOfEnder extends CoreThrowableProjectile implements EyeOfEnder {

	public CoreEyeOfEnder(EntityType type, UUID uuid) {
		super(type, uuid);
	}

	@Override
	public ProjectileType getProjectileType() {
		return ProjectileType.EYE_OF_ENDER;
	}

}
