package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.Bogged;
import de.atlasmc.entity.EntityType;

public class CoreBogged extends CoreAbstractSkeleton implements Bogged {

	public CoreBogged(EntityType type, UUID uuid) {
		super(type, uuid);
	}

}
