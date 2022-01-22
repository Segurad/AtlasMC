package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.Endermite;
import de.atlasmc.entity.EntityType;
import de.atlasmc.world.World;

public class CoreEndermite extends CoreMob implements Endermite {

	public CoreEndermite(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
