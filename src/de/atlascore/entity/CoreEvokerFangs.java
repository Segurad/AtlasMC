package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.EvokerFangs;
import de.atlasmc.world.World;

public class CoreEvokerFangs extends CoreEntity implements EvokerFangs {

	public CoreEvokerFangs(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
