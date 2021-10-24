package de.atlasmc.world;

import de.atlasmc.util.nbt.NBTHolder;

public interface Dimension extends NBTHolder {
	
	public String getName();
	
	public int getID();
	
	public String getInfiniburn();
	
	public String getEffects();
	
	public boolean isPiglinSafe();
	
	public boolean isNatural();
	
	public boolean isAllowRespawnAnchor();
	
	public boolean isSkylight();
	
	public boolean isAllowBeds();
	
	public boolean isRaids();
	
	public boolean isUltrawarm();
	
	public boolean isCeiling();
	
	public float getAmbientLight();
	
	public float getCoordinateScale();
	
	public long getFixedTime();
	
	public int getLogicalHeight();

}
