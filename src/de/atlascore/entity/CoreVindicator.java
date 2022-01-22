package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Vindicator;
import de.atlasmc.world.World;

public class CoreVindicator extends CoreRaider implements Vindicator {

	public CoreVindicator(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
