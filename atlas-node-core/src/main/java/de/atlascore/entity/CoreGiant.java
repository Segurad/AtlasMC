package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Giant;

public class CoreGiant extends CoreMob implements Giant {

	public CoreGiant(EntityType type, UUID uuid) {
		super(type, uuid);
	}

}
