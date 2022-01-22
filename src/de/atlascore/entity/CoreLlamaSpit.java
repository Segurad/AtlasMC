package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.LlamaSpit;
import de.atlasmc.world.World;

public class CoreLlamaSpit extends CoreAbstractProjectile implements LlamaSpit {

	public CoreLlamaSpit(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	public ProjectileType getProjectileType() {
		return ProjectileType.LLAMA_SPIT;
	}

}
