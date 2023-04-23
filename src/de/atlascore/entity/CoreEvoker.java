package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Evoker;

public class CoreEvoker extends CoreSpellcasterIllager implements Evoker {

	public CoreEvoker(EntityType type, UUID uuid) {
		super(type, uuid);
	}

}
