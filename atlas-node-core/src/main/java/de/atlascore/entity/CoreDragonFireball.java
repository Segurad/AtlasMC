package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.DragonFireball;
import de.atlasmc.entity.EntityType;

public class CoreDragonFireball extends CoreAbstractFireball implements DragonFireball {

	public CoreDragonFireball(EntityType type, UUID uuid) {
		super(type, uuid);
	}

	@Override
	public ProjectileType getProjectileType() {
		return ProjectileType.DRAGON_FIREBALL;
	}

}
