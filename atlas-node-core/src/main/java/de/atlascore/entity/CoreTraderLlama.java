package de.atlascore.entity;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.TraderLlama;

public class CoreTraderLlama extends CoreLlama implements TraderLlama {

	private int despawnDelay;
	
	public CoreTraderLlama(EntityType type) {
		super(type);
	}

	@Override
	public int getDespawnDelay() {
		return despawnDelay;
	}

	@Override
	public void setDespawnDelay(int delay) {
		this.despawnDelay = delay;
	}

}
