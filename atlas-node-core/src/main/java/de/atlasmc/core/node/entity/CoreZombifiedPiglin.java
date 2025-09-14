package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.ZombifiedPiglin;

public class CoreZombifiedPiglin extends CoreZombie implements ZombifiedPiglin {
	
	private int angerTime;
	private boolean angry;
	
	public CoreZombifiedPiglin(EntityType type) {
		super(type);
	}

	@Override
	public void setAngerTime(int ticks) {
		this.angerTime = ticks;
	}

	@Override
	public int getAngerTime() {
		return angerTime;
	}

	@Override
	public boolean isAngry() {
		return angry;
	}

	@Override
	public void setAngry(boolean angry) {
		this.angry = angry;
	}

}
