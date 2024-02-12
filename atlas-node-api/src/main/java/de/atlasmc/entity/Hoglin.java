package de.atlasmc.entity;

public interface Hoglin extends Animal {
	
	public boolean isImmune();
	
	public void setImmune(boolean immune);

	public void setHuntable(boolean huntable);
	
	public boolean isHuntable();

}
