package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.LeashKnot;
import de.atlasmc.world.World;

public class CoreLeashKnot extends CoreEntity implements LeashKnot {

	public CoreLeashKnot(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
