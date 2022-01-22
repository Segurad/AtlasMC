package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.SkeletonHorse;
import de.atlasmc.world.World;

public class CoreSkeletonHorse extends CoreAbstractHorse implements SkeletonHorse {

	public CoreSkeletonHorse(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
