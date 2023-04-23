package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.SmallFireball;

public class CoreSmallFireball extends CoreSizedFireball implements SmallFireball {

	public CoreSmallFireball(EntityType type, UUID uuid) {
		super(type, uuid);
	}

	@Override
	public ProjectileType getProjectileType() {
		return ProjectileType.SMALL_FIREBALL;
	}

}
