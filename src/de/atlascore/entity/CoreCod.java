package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.Cod;
import de.atlasmc.entity.EntityType;
import de.atlasmc.world.World;

public class CoreCod extends CoreFish implements Cod {

	public CoreCod(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
