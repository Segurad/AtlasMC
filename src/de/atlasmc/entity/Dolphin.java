package de.atlasmc.entity;

import de.atlasmc.Location;

public interface Dolphin extends WaterAnimal {
	
	public Location getTreasurePosition();
	public boolean canFindTreasure();
	public boolean hasFish();

}
