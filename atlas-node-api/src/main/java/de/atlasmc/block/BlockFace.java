package de.atlasmc.block;

import java.util.List;

import org.joml.Vector3d;

import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public enum BlockFace implements EnumName, EnumValueCache {
	NORTH(0, 0, -1, 0f, -180f, 8, 2),
	EAST(1, 0, 0, 0f, -90f, 12, 5),
	SOUTH(0, 0, 1, 0f, 0f, 0, 3),
	WEST(-1, 0, 0, 0f, 90f, 4, 4),
	UP(0, 1, 0, -90f, 0f, -1, 1),
	DOWN(0, -1, 0, 90f, 0f, -2, 0),
	NORTH_EAST(1, 0, -1, 10),
	NORTH_WEST(-1, 0, -1, 6),
	SOUTH_EAST(1, 0, 1, 14),
	SOUTH_WEST(-1, 0, 1, 2),
	WEST_NORTH_WEST(-2, 0, -1, 5),
	NORTH_NORTH_WEST(-1, 0, -2, 7),
	NORTH_NORTH_EAST(1, 0, -2, 9),
	EAST_NORTH_EAST(2, 0, -1, 11),
	EAST_SOUTH_EAST(2, 0, 1, 13),
	SOUTH_SOUTH_EAST(1, 0, 2, 15),
	SOUTH_SOUTH_WEST(-1, 0, 2, 1),
	WEST_SOUTH_WEST(-2, 0, 1, 3);
	
	private static List<BlockFace> VALUES;
	
	private final String name;
	
	private final int faceID;
	private final int modX;
	private final int modY;
	private final int modZ;
	private final float pitch;
	private final float yaw;
	private final int rotation;
	
	private BlockFace(int modX, int modY, int modZ, int rotation) {
		this(modX, modY, modZ, 0, 0, rotation, -1);
	}
	
	private BlockFace(int modX, int modY, int modZ, float pitch, float yaw, int rotation, int faceID) {
		this.faceID = faceID;
		this.modX = modX;
		this.modY = modY;
		this.modZ = modZ;
		this.pitch = pitch;
		this.yaw = yaw;
		this.rotation = rotation;
		this.name = name().toLowerCase().intern();
	}
	
	public int getRotation() {
		return rotation;
	}
	
	@Override
	public String getName() {
		return name;
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
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		List<BlockFace> values = getValues();
		final int size = values.size();
		for (int i = 0; i < size; i++) {
			BlockFace value = values.get(i);
			if (value.name.equals(name)) {
				return value;
			};
		}
		return null;
	}
	
	public static BlockFace getByRotation(int rotation) {
		List<BlockFace> values = getValues();
		final int size = values.size();
		for (int i = 0; i < size; i++) {
			BlockFace value = values.get(i);
			if (value.rotation == rotation) {
				return value;
			};
		}
		return null;
	}
	
	public static BlockFace getByFaceID(int facing) {
		switch(facing) {
		case 0:
			return DOWN;
		case 1:
			return UP;
		case 2:
			return NORTH;
		case 3:
			return SOUTH;
		case 4:
			return WEST;
		case 5:
			return EAST;
		default:
			throw new IllegalArgumentException("Invalid face id: " + facing);
		}
	}
	
	public int getFaceID() {
		return faceID;
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
