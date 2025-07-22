package de.atlascore.entity;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Salmon;

public class CoreSalmon extends CoreFish implements Salmon {

	private Type type = Type.MEDIUM;
	
	public CoreSalmon(EntityType type) {
		super(type);
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
