package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Marker;

public class CoreMarker extends CoreEntity implements Marker {

	public CoreMarker(EntityType type, UUID uuid) {
		super(type, uuid);
	}

}
