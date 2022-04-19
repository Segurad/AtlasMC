package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.ZombifiedPiglin;
import de.atlasmc.world.World;

public class CoreZombifiedPiglin extends CoreZombie implements ZombifiedPiglin {

	public CoreZombifiedPiglin(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
