package de.atlasmc.entity;

import de.atlasmc.DyeColor;

public interface Wolf extends Tameable {
	
	public boolean isBegging();
	
	public void setBegging(boolean begging);
	
	public DyeColor getCollarColor();
	
	public void setCollarColor(DyeColor color);
	
	public int getAnger();
	
	public void setAnger(int anger);

}
