package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.MagmaCube;
import de.atlasmc.world.World;

public class CoreMagmaCube extends CoreAbstractSlime implements MagmaCube {

	public CoreMagmaCube(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
