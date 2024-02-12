package de.atlasmc.world;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.nbt.NBTHolder;

public interface Dimension extends NBTHolder, Namespaced {
	
	public int getID();
	
	public int getMinY();
	
	public int getHeight();
	
	public float getAmbientLight();
	
	/**
	 * Returns the fixed time value of this dimension or -1 if no fixed time is set
	 * @return time or -1
	 */
	public long getFixedTime();
	
	public boolean hasFixedTime();
	
	public boolean hasSkyLight();
	
	public boolean hasCeiling();
	
	public int getLogicalHeight();
	
	public NamespacedKey getEffects();

}
