package de.atlascore.entity;

import de.atlasmc.entity.Chicken;
import de.atlasmc.entity.EntityType;

public class CoreChicken extends CoreAgeableMob implements Chicken {
	
	private int eggLayTime = -1;
	private boolean isChickenJockey;
	
	public CoreChicken(EntityType type) {
		super(type);
	}

	@Override
	public void setEggLayTime(int time) {
		this.eggLayTime = time;
	}

	@Override
	public int getEggLayTime() {
		return eggLayTime;
	}
	
	@Override
	public boolean isChickenJockey() {
		return isChickenJockey;
	}

	@Override
	public void setChickenJocken(boolean value) {
		this.isChickenJockey = value;
	}

}
