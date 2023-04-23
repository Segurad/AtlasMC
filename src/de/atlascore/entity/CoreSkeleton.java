package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Skeleton;

public class CoreSkeleton extends CoreAbstractSkeleton implements Skeleton {

	public CoreSkeleton(EntityType type, UUID uuid) {
		super(type, uuid);
	}

}
