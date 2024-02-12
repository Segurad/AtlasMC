package de.atlasmc.entity;

import de.atlasmc.DyeColor;

public interface Sheep extends Animal {
	
	public DyeColor getColor();
	
	public void setColor(DyeColor color);
	
	public boolean isSheared();

	public void setSheared(boolean sheared);
	
}
