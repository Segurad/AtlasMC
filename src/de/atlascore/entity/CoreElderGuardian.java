package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.ElderGuardian;
import de.atlasmc.entity.EntityType;
import de.atlasmc.world.World;

public class CoreElderGuardian extends CoreGuardian implements ElderGuardian {

	public CoreElderGuardian(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
