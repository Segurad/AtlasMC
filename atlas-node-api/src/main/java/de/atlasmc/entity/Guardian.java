package de.atlasmc.entity;

public interface Guardian extends Monster {
	
	public boolean isRetractingSpikes();
	
	public Entity getTarget();
	
	public boolean hasTarget();
	
	public void setTarget(Entity target);
	
	public void setRetractingSpikes(boolean retracting);

}
