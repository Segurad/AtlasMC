package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.TraderLlama;
import de.atlasmc.world.World;

public class CoreTraderLlama extends CoreLlama implements TraderLlama {

	public CoreTraderLlama(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
