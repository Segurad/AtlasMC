package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Egg;
import de.atlasmc.world.World;

public class CoreEgg extends CoreThrowableProjectile implements Egg {

	public CoreEgg(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	public ProjectileType getProjectileType() {
		return ProjectileType.EGG;
	}

}
