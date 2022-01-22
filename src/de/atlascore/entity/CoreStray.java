package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Stray;
import de.atlasmc.world.World;

public class CoreStray extends CoreMob implements Stray {

	public CoreStray(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
