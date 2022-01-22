package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.ExpBottle;
import de.atlasmc.world.World;

public class CoreExpBottle extends CoreThrowableProjectile implements ExpBottle {

	public CoreExpBottle(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	public ProjectileType getProjectileType() {
		return ProjectileType.EXP_BOTTLE;
	}

}
