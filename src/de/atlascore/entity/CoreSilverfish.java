package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Silverfish;

public class CoreSilverfish extends CoreMob implements Silverfish {

	public CoreSilverfish(EntityType type, UUID uuid) {
		super(type, uuid);
	}

}
