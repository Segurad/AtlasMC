package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.LargeFireball;
import de.atlasmc.world.World;

public class CoreLargeFireball extends CoreSizedFireball implements LargeFireball {

	public CoreLargeFireball(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	public ProjectileType getProjectileType() {
		return ProjectileType.LARGE_FIREBALL;
	}

}
