package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.Cow;
import de.atlasmc.entity.EntityType;

public class CoreCow extends CoreAgeableMob implements Cow {

	public CoreCow(EntityType type, UUID uuid) {
		super(type, uuid);
	}

}
