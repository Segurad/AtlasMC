package de.atlasmc.entity;

import de.atlasmc.DyeColor;

public interface Wolf extends TameableAnimal {
	
	public boolean isBegging();
	public DyeColor getCollarColor();
	public int getAnger();

}
