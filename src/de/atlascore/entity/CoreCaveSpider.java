package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.CaveSpider;
import de.atlasmc.entity.EntityType;

public class CoreCaveSpider extends CoreSpider implements CaveSpider {

	public CoreCaveSpider(EntityType type, UUID uuid) {
		super(type, uuid);
	}

}
