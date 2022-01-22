package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.ZombieHorse;
import de.atlasmc.world.World;

public class CoreZombieHorse extends CoreAbstractHorse implements ZombieHorse {

	public CoreZombieHorse(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
