package de.atlasmc.block;

import java.util.List;

import org.joml.Vector3d;

public enum BlockFace {
	NORTH(0, 0, -1, 0f, -180f),
	EAST(1, 0, 0, 0f, -90f),
	SOUTH(0, 0, 1, 0f, 0f),
	WEST(-1, 0, 0, 0f, 90f),
	UP(0, 1, 0, -90f, 0f),
	DOWN(0, -1, 0, 90f, 0f),
	NORTH_EAST(1, 0, -1),
	NORTH_WEST(-1, 0, -1),
	SOUTH_EAST(1, 0, 1),
	SOUTH_WEST(-1, 0, 1),
	WEST_NORTH_WEST(-2, 0, -1),
	NORTH_NORTH_WEST(-1, 0, -2),
	NORTH_NORTH_EAST(1, 0, -2),
	EAST_NORTH_EAST(2, 0, -1),
	EAST_SOUTH_EAST(2, 0, 1),
	SOUTH_SOUTH_EAST(1, 0, 2),
	SOUTH_SOUTH_WEST(-1, 0, 2),
	WEST_SOUTH_WEST(-2, 0, 1);
	
	private static List<BlockFace> VALUES;
	
	final int modX, modY, modZ; // Modifier in direction*
	final float pitch, yaw;
	
	private BlockFace(int modX, int modY, int modZ) {
		this(modX, modY, modZ, 0, 0);
	}
	
	private BlockFace(int modX, int modY, int modZ, float pitch, float yaw) {
		this.modX = modX;
		this.modY = modY;
		this.modZ = modZ;
		this.pitch = pitch;
		this.yaw = yaw;
	}
	
	/**
	 * Returns the pitch facing to
	 * @return pitch
	 */
	public float getPitch() {
		return pitch;
	}
	
	/**
	 * Returns the yaw facing to
	 * @return yaw
	 */
	public float getYaw() {
		return yaw;
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
	
	@SuppressWarnings("unchecked")
	public <T extends Vector3d> T modifiy(T loc) {
		return (T) loc.add(modX, modY, modZ);
	}

	public static BlockFace getByName(String name) {
		name = name.toUpperCase();
		for (BlockFace face : values()) {
			if (face.name().equals(name)) {
				return face;
			};
		}
		return null;
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<BlockFace> getValues() {
		if (VALUES == null)
			VALUES = List.of(values());
		return VALUES;
	}
	
	/**
	 * Releases the system resources used from the values cache
	 */
	public static void freeValues() {
		VALUES = null;
	}

}
