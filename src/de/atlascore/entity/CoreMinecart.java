package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Minecart;

public class CoreMinecart extends CoreAbstractMinecart implements Minecart {

	public CoreMinecart(EntityType type, UUID uuid) {
		super(type, uuid);
	}

}
