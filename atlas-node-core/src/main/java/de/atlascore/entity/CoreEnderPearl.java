package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EnderPearl;
import de.atlasmc.entity.EntityType;

public class CoreEnderPearl extends CoreThrowableProjectile implements EnderPearl {

	public CoreEnderPearl(EntityType type, UUID uuid) {
		super(type, uuid);
	}

	@Override
	public ProjectileType getProjectileType() {
		return ProjectileType.ENDER_PEARL;
	}

}
