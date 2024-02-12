package de.atlasmc.entity;

public interface Warden extends Monster {
	
	int getAnger(Entity entity);
	
	int setAnger(Entity entity, int value);
	
	int clearAnger(Entity entity);
	
	int getAnger();

}
