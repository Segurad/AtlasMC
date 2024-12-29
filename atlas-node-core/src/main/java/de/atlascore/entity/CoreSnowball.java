package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Snowball;

public class CoreSnowball extends CoreThrowableProjectile implements Snowball {

	public CoreSnowball(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
}
