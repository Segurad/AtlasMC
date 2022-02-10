package de.atlasmc;

import de.atlasmc.block.Block;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.world.World;

public class Location extends SimpleLocation {

	private World world;
	
	public Location(World world, SimpleLocation loc) {
		super(loc);
		this.world = world;
	}
	
	public Location(World world, double x, double y, double z) {
		super(x, y, z);
		this.world = world;
	}
	
	public Location(Location loc) {
		super(loc);
		this.world = loc.world;
	}
	
	public Location(World world) {
		this(world, 0, 0, 0);
	}

	public World getWorld() {
		return world;
	}

	public Block getBlock() {
		return world.getBlock(getBlockX(), getBlockY(), getBlockZ());
	}
	
	@Override
	public Location clone() {
		return (Location) super.clone();
	}
	
	@Override
	public Location convertToBlock() {
		return (Location) super.convertToBlock();
	}

	public BlockData getBlockData() {
		return world.getBlockData(getBlockX(), getBlockY(), getBlockZ());
	}
	
	public Location setLocation(World world, double x, double y, double z) {
		super.setLocation(x, y, z);
		this.world = world;
		return this;
	}
	
	public Location setLocation(World world, double x, double y, double z, float yaw, float pitch) {
		super.setLocation(x, y, z, yaw, pitch);
		this.world = world;
		return this;
	}
	
	public Location copyTo(Location loc) {
		super.copyTo(loc);
		loc.world = world;
		return loc;
	}
	
	public Location setLocation(Location loc) {
		super.setLocation(loc);
		world = loc.world;
		return this;
	}

}
