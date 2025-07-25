package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.Donkey;
import de.atlasmc.entity.EntityType;

public class CoreDonkey extends CoreChestedHorse implements Donkey {

	public CoreDonkey(EntityType type, UUID uuid) {
		super(type);
	}

}
