package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Giant;
import de.atlasmc.world.World;

public class CoreGiant extends CoreMob implements Giant {

	public CoreGiant(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
