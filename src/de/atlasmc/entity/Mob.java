package de.atlasmc.entity;

public interface Mob extends LivingEntity {
	
	public boolean isAggressive();
	
	public void setAggressive(boolean aggressive);
	
	public boolean isAware();
	
	public void setAware(boolean aware);
	
	public boolean isLeftHanded();
	
	public void setLeftHanded(boolean left);

}
