package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Husk;
import de.atlasmc.world.World;

public class CoreHusk extends CoreZombie implements Husk {

	public CoreHusk(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
