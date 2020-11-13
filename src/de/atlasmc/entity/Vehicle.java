package de.atlasmc.entity;

import de.atlasmc.Vector;

public interface Vehicle extends Entity {
	
	public Vector getVelocity();
	public void setVelocity(Vector vel);

}
