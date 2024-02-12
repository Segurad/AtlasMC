package de.atlasmc.entity;

public interface Camel extends AbstractHorse {
	
	long getLastPoseTick();
	
	void setLastPoseTick(long pose);
	
	boolean isDashing();
	
	void setDashing(boolean dashing);

}
