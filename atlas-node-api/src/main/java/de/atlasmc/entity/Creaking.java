package de.atlasmc.entity;

import org.joml.Vector3i;

public interface Creaking extends Monster {
	
	Vector3i getHome();
	
	void setHome(Vector3i position);

}
