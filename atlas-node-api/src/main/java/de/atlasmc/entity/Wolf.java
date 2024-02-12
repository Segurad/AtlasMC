package de.atlasmc.entity;

import de.atlasmc.DyeColor;

public interface Wolf extends Tameable {
	
	public boolean isBegging();
	
	public void setBegging(boolean begging);
	
	public DyeColor getCollarColor();
	
	public void setCollarColor(DyeColor color);
	
	/**
	 * Returns the time in ticks until the angry state of this Wolf will be reset or -1 if no time
	 * @return ticks or -1
	 */
	public int getAngerTime();
	
	public void setAngerTime(int ticks);
	
	public void setAngry(boolean angry);
	
	public boolean isAngry();

}
