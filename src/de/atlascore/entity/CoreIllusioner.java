package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Illusioner;

public class CoreIllusioner extends CoreSpellcasterIllager implements Illusioner {
	
	public CoreIllusioner(EntityType type, UUID uuid) {
		super(type, uuid);
	}

}
