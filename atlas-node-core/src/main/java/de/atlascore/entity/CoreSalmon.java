package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Salmon;

public class CoreSalmon extends CoreFish implements Salmon {

	public CoreSalmon(EntityType type, UUID uuid) {
		super(type, uuid);
	}

}
