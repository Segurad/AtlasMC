package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Skeleton;
import de.atlasmc.world.World;

public class CoreSkeleton extends CoreMob implements Skeleton {

	public CoreSkeleton(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
