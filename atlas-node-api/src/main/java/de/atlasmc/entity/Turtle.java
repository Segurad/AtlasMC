package de.atlasmc.entity;

import de.atlasmc.Location;
import de.atlasmc.SimpleLocation;

public interface Turtle extends Animal {
	
	public default Location getHome() {
		return getHome(new Location(getWorld()));
	}

	public Location getHome(Location loc);
	
	public default void setHome(SimpleLocation loc) {
		setHome(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}
	
	public void setHome(int x, int y, int z);
	
	public boolean hasEgg();
	
	public void setEgg(boolean egg);
	
	public boolean hasLayingEgg();
	
	public void setLayingEgg(boolean laying);
	
	public default Location getTravelPos() {
		return getTravelPos(new Location(getWorld()));
	}
	
	public Location getTravelPos(Location loc);
	
	public default void setTravelPos(SimpleLocation loc) {
		setTravelPos(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}
	
	public void setTravelPos(int x, int y, int z);
	
	public boolean isGoingHome();
	
	public void setGoingHome(boolean home);
	
	public boolean isTraveling();

	public void setTraveling(boolean traveling);
	
}
