package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Mule;
import de.atlasmc.world.World;

public class CoreMule extends CoreChestedHorse implements Mule {

	public CoreMule(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
