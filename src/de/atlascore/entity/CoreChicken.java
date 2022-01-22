package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.Chicken;
import de.atlasmc.entity.EntityType;
import de.atlasmc.world.World;

public class CoreChicken extends CoreAgeableMob implements Chicken {

	public CoreChicken(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
