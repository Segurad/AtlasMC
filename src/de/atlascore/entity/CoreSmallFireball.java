package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.SmallFireball;
import de.atlasmc.world.World;

public class CoreSmallFireball extends CoreSizedFireball implements SmallFireball {

	public CoreSmallFireball(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	public ProjectileType getProjectileType() {
		return ProjectileType.SMALL_FIREBALL;
	}

}
