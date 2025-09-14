package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Salmon;

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
