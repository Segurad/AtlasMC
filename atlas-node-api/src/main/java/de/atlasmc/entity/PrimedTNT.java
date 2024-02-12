package de.atlasmc.entity;

public interface PrimedTNT extends Entity {
	
	int getFuseTime();
	
	void setFuseTime(int time);
	
	Entity getSource();
	
	void setSource(Entity source);

}
