package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.Cod;
import de.atlasmc.entity.EntityType;

public class CoreCod extends CoreFish implements Cod {

	public CoreCod(EntityType type, UUID uuid) {
		super(type, uuid);
	}

}
