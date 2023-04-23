package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.Drowned;
import de.atlasmc.entity.EntityType;

public class CoreDrowned extends CoreZombie implements Drowned {

	public CoreDrowned(EntityType type, UUID uuid) {
		super(type, uuid);
	}

}
