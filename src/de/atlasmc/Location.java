package de.atlasmc;

import de.atlasmc.world.World;

public class Location extends SimpleLocation {

	private final World world;
	
	public Location(World world, double x, double y, double z) {
		super(x, y, z);
		this.world = world;
	}
	
	public World getWorld() {
		return world;
	}

}
