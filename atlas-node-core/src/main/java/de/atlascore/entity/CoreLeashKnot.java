package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.LeashKnot;

public class CoreLeashKnot extends CoreEntity implements LeashKnot {

	public CoreLeashKnot(EntityType type, UUID uuid) {
		super(type, uuid);
	}

}
