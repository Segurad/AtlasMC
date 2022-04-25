package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Snowball;
import de.atlasmc.world.World;

public class CoreSnowball extends CoreThrowableProjectile implements Snowball {

	public CoreSnowball(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	public ProjectileType getProjectileType() {
		return ProjectileType.SNOWBALL;
	}

}
