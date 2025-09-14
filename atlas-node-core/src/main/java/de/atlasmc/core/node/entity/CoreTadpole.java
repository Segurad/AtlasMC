package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Tadpole;

public class CoreTadpole extends CoreFish implements Tadpole {
	
	private int age;
	
	public CoreTadpole(EntityType type) {
		super(type);
	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public void setAge(int age) {
		this.age = age;
	}
	
}
