package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Vehicle;

public class CoreVehicle extends CoreEntity implements Vehicle {

	public CoreVehicle(EntityType type, UUID uuid) {
		super(type, uuid);
	}

}
