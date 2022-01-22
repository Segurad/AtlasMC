package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.EyeOfEnder;
import de.atlasmc.world.World;

public class CoreEyeOfEnder extends CoreThrowableProjectile implements EyeOfEnder {

	public CoreEyeOfEnder(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	public ProjectileType getProjectileType() {
		return ProjectileType.EYE_OF_ENDER;
	}

}
