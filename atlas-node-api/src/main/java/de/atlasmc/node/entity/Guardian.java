package de.atlasmc.node.entity;

public interface Guardian extends Monster {
	
	boolean isRetractingSpikes();
	
	Entity getTarget();
	
	boolean hasTarget();
	
	void setTarget(Entity target);
	
	void setRetractingSpikes(boolean retracting);

}
