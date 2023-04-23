package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.PiglinBrute;

public class CorePiglinBrute extends CoreAbstractPiglin implements PiglinBrute {

	public CorePiglinBrute(EntityType type, UUID uuid) {
		super(type, uuid);
	}

}
