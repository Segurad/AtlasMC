package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Ravager;
import de.atlasmc.world.World;

public class CoreRavager extends CoreRaider implements Ravager {

	public CoreRavager(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
