package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Vehicle;
import de.atlasmc.world.World;

public class CoreVehicle extends CoreEntity implements Vehicle {

	public CoreVehicle(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
