package de.atlasmc;

import de.atlasmc.block.Block;
import de.atlasmc.world.World;

public class Location extends SimpleLocation {

	private final World world;
	
	public Location(World world, double x, double y, double z) {
		super(x, y, z);
		this.world = world;
	}
	
	public Location(Location loc) {
		super(loc);
		this.world = loc.world;
	}
	
	public World getWorld() {
		return world;
	}

	public Block getBlock() {
		return null;
	}
	
	public Location clone() {
		return new Location(this);
	}

}
