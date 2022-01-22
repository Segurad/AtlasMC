package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.WitherSkeleton;
import de.atlasmc.world.World;

public class CoreWitherSkeleton extends CoreMob implements WitherSkeleton {

	public CoreWitherSkeleton(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
