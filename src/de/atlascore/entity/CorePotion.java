package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Potion;
import de.atlasmc.world.World;

public class CorePotion extends CoreThrowableProjectile implements Potion {

	public CorePotion(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	public ProjectileType getProjectileType() {
		return ProjectileType.POTION;
	}

}
