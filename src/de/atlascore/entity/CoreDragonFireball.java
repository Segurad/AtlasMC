package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.DragonFireball;
import de.atlasmc.entity.EntityType;
import de.atlasmc.world.World;

public class CoreDragonFireball extends CoreFireball implements DragonFireball {

	public CoreDragonFireball(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	public ProjectileType getProjectileType() {
		return ProjectileType.DRAGON_FIREBALL;
	}

}
