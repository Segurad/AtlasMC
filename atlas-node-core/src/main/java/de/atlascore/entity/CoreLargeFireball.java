package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.LargeFireball;

public class CoreLargeFireball extends CoreSizedFireball implements LargeFireball {

	public CoreLargeFireball(EntityType type, UUID uuid) {
		super(type, uuid);
	}

}
