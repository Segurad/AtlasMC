package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.TraderLlama;

public class CoreTraderLlama extends CoreLlama implements TraderLlama {

	public CoreTraderLlama(EntityType type, UUID uuid) {
		super(type, uuid);
	}

}
