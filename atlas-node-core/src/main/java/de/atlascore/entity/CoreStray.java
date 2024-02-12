package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Stray;

public class CoreStray extends CoreAbstractSkeleton implements Stray {

	public CoreStray(EntityType type, UUID uuid) {
		super(type, uuid);
	}

}
