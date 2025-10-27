package de.atlasmc.node;

import de.atlasmc.node.block.Block;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.world.World;

public class WorldLocation extends Location {

	private World world;
	
	public WorldLocation(World world, Location loc) {
		super(loc);
		this.world = world;
	}
	
	public WorldLocation(World world, double x, double y, double z) {
		super(x, y, z);
		this.world = world;
	}
	
	public WorldLocation(WorldLocation loc) {
		super(loc);
		this.world = loc.world;
	}
	
	public WorldLocation() {
		super(0, 0, 0);
	}
	
	public WorldLocation(World world) {
		this(world, 0, 0, 0);
	}

	public World getWorld() {
		return world;
	}
	
	public WorldLocation setWorld(World world) {
		this.world = world;
		return this;
	}

	public Block getBlock() {
		return world.getBlock(getBlockX(), getBlockY(), getBlockZ());
	}
	
	@Override
	public WorldLocation clone() {
		return (WorldLocation) super.clone();
	}
	
	@Override
	public WorldLocation convertToBlock() {
		return (WorldLocation) super.convertToBlock();
	}

	public BlockData getBlockData() {
		return world.getBlockData(getBlockX(), getBlockY(), getBlockZ());
	}
	
	public WorldLocation set(World world, double x, double y, double z) {
		super.set(x, y, z);
		this.world = world;
		return this;
	}
	
	public WorldLocation set(World world, double x, double y, double z, float yaw, float pitch) {
		super.set(x, y, z, yaw, pitch);
		this.world = world;
		return this;
	}
	
	/**
	 * Copies all values from this location to the given one and returns it
	 * @param loc
	 * @return location
	 */
	public WorldLocation copyTo(WorldLocation loc) {
		super.copyTo(loc);
		loc.world = world;
		return loc;
	}
	
	public WorldLocation set(WorldLocation loc) {
		super.set(loc);
		world = loc.world;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((world == null) ? 0 : world.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorldLocation other = (WorldLocation) obj;
		if (world == null) {
			if (other.world != null)
				return false;
		} else if (!world.equals(other.world))
			return false;
		return true;
	}

}
