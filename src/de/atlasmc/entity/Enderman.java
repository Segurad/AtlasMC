package de.atlasmc.entity;

import de.atlasmc.Material;

public interface Enderman extends Monster {

	public Material getCarriedBlock();
	public boolean isScreaming();
	public boolean isStaring();
	
}
