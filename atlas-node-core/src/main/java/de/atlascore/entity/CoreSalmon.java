package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Salmon;

public class CoreSalmon extends CoreFish implements Salmon {

	private Type type;
	
	public CoreSalmon(EntityType type, UUID uuid) {
		super(type, uuid);
	}

	@Override
	public Type getSalmonType() {
		return type;
	}

	@Override
	public void setSalmonType(Type type) {
		this.type = type;
	}

}
