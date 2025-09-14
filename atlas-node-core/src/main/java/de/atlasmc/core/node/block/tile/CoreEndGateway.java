package de.atlasmc.core.node.block.tile;

import de.atlasmc.node.Location;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.tile.EndGateway;
import de.atlasmc.node.world.Chunk;

public class CoreEndGateway extends CoreTileEntity implements EndGateway {
	
	private long age;
	private boolean exactTeleport;
	private boolean relativeCoordinates;
	private final Location exit;
	
	public CoreEndGateway(BlockType type) {
		super(type);
		exit = new Location(null, Double.NaN, Double.NaN, Double.NaN);
	}

	@Override
	public long getAge() {
		return age;
	}

	@Override
	public void setAge(long age) {
		this.age = age;
	}

	@Override
	public boolean isExactTeleport() {
		return exactTeleport;
	}

	@Override
	public void setExactTeleport(boolean exact) {
		this.exactTeleport = exact;
	}

	@Override
	public boolean isRelativeCoordinates() {
		return relativeCoordinates;
	}

	@Override
	public void setRelativeCoordinates(boolean relative) {
		this.relativeCoordinates = relative;
	}

	@Override
	public Location getExitPortal() {
		return exit;
	}

	@Override
	public void setExitPortal(Location loc) {
		exit.set(loc);
	}
	
	@Override
	public void setLocation(Chunk chunk, int x, int y, int z) {
		super.setLocation(chunk, x, y, z);
		if (exit.getWorld() == null && chunk != null) {
			if (exit.isInvalid()) {
				exit.set(getWorld(), getX(), getY(), getZ());
			} else {
				exit.set(getWorld(), exit.x, exit.y, exit.z);
			}
		}
			
	}

}
