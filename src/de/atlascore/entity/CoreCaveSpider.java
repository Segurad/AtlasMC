package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.CaveSpider;
import de.atlasmc.entity.EntityType;
import de.atlasmc.world.World;

public class CoreCaveSpider extends CoreSpider implements CaveSpider {

	public CoreCaveSpider(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
