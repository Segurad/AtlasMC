package de.atlasmc.block;

import de.atlasmc.SimpleLocation;

public enum BlockFace {
	NORTH(0, 0, -1),
	EAST(1, 0, 0),
	SOUTH(0, 0, 1),
	WEST(-1, 0, 0),
	UP(0, 1, 0),
	DOWN(0, -1, 0),
	NORTH_EAST(1, 0, -1),
	NORTH_WEST(-1, 0, -1),
	SOUTH_EAST(1, 0, 1),
	SOUTH_WEST(-1, 0, 1),
	WEST_NORTH_WEST(0, 0, 0), // TODO maybe
	NORTH_NORTH_WEST(0, 0, 0),
	NORTH_NORTH_EAST(0, 0, 0),
	EAST_NORTH_EAST(0, 0, 0),
	EAST_SOUTH_EAST(0, 0, 0),
	SOUTH_SOUTH_EAST(0, 0, 0),
	SOUTH_SOUTH_WEST(0, 0, 0),
	WEST_SOUTH_WEST(0, 0, 0);
	
	final int modX, modY, modZ; // Modifier in direction*
	
	private BlockFace(int modX, int modY, int modZ) {
		this.modX = modX;
		this.modY = modY;
		this.modZ = modZ;
	}
	
	public int getModX() {
		return modX;
	}
	
	public int getModY() {
		return modY;
	}
	
	public int getModZ() {
		return modZ;
	}
	
	public SimpleLocation mod(SimpleLocation loc) {
		return loc.add(modX, modY, modZ);
	}

	public static BlockFace getByName(String name) {
		BlockFace[] faces = values();
		for (BlockFace face : faces) {
			if (face.name().equalsIgnoreCase(name)) {
				return face;
			};
		}
		return null;
	}

}
