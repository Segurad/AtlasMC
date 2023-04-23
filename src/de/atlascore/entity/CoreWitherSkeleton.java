package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.WitherSkeleton;

public class CoreWitherSkeleton extends CoreAbstractSkeleton implements WitherSkeleton {

	public CoreWitherSkeleton(EntityType type, UUID uuid) {
		super(type, uuid);
	}

}
