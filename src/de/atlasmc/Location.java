package de.atlasmc;

import de.atlasmc.block.Block;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.world.World;

public class Location extends SimpleLocation {

	private World world;
	
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
		return world.getBlock(getBlockX(), getBlockY(), getBlockZ());
	}
	
	public Location clone() {
		return new Location(this);
	}

	public BlockData getBlockData() {
		return world.getBlockData(getBlockX(), getBlockY(), getBlockZ());
	}
	
	public Location copyTo(Location loc) {
		super.copyTo(loc);
		loc.world = world;
		return loc;
	}

}
