package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Squid;
import de.atlasmc.world.World;

public class CoreSquid extends CoreMob implements Squid {

	public CoreSquid(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
