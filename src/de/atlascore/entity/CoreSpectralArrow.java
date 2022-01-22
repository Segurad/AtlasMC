package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.SpectralArrow;
import de.atlasmc.world.World;

public class CoreSpectralArrow extends CoreAbstractArrow implements SpectralArrow {

	public CoreSpectralArrow(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	public ProjectileType getProjectileType() {
		return ProjectileType.SPECTRAL_ARROW;
	}

}
