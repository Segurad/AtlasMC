package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Salmon;
import de.atlasmc.world.World;

public class CoreSalmon extends CoreFish implements Salmon {

	public CoreSalmon(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
