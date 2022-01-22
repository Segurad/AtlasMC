package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EnderPearl;
import de.atlasmc.entity.EntityType;
import de.atlasmc.world.World;

public class CoreEnderPearl extends CoreThrowableProjectile implements EnderPearl {

	public CoreEnderPearl(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	public ProjectileType getProjectileType() {
		return ProjectileType.ENDER_PEARL;
	}

}
