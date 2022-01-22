package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.Donkey;
import de.atlasmc.entity.EntityType;
import de.atlasmc.world.World;

public class CoreDonkey extends CoreChestedHorse implements Donkey {

	public CoreDonkey(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
