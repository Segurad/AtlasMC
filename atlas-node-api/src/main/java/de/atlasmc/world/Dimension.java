package de.atlasmc.world;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.nbt.NBTHolder;

public interface Dimension extends NBTHolder, Namespaced {
	
	int getID();
	
	/**
	 * Returns the minimum height of this dimension.
	 * Always a multiple of 16
	 * @return y
	 */
	int getMinY();
	
	/**
	 * Returns the maximum height of this dimension.
	 * Always a multiple of 16
	 * @return
	 */
	int getHeight();
	
	float getAmbientLight();
	
	/**
	 * Returns the fixed time value of this dimension or -1 if no fixed time is set
	 * @return time or -1
	 */
	long getFixedTime();
	
	boolean hasFixedTime();
	
	boolean hasSkyLight();
	
	boolean hasCeiling();
	
	int getLogicalHeight();
	
	NamespacedKey getEffects();

}
