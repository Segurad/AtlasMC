package de.atlasmc.entity;

import de.atlasmc.Location;

public interface Turtle extends Animal {
	
	public Location getHome();
	public boolean hasEgg();
	public boolean hasLayingEgg();
	public Location getTravelPos();
	public boolean isGoingHome();
	public boolean isTraveling();
}
