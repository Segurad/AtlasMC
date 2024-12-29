package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.LlamaSpit;

public class CoreLlamaSpit extends CoreAbstractProjectile implements LlamaSpit {

	public CoreLlamaSpit(EntityType type, UUID uuid) {
		super(type, uuid);
	}

}
