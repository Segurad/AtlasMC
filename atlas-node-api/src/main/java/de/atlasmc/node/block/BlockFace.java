package de.atlasmc.node.block;

import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector3i;
import de.atlasmc.util.EnumName;

public enum BlockFace implements EnumName {
	
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
	
	private static final BlockFace[] BY_FACE_ID = new BlockFace[]{ 
			DOWN,
			UP,
			NORTH,
			SOUTH,
			WEST,
			EAST 
	};
	
	private static final BlockFace[] BY_ROTATION = new BlockFace[] {
			SOUTH,
			SOUTH_SOUTH_WEST,
			SOUTH_WEST,
			WEST_SOUTH_WEST,
			WEST,
			WEST_NORTH_WEST,
			NORTH_WEST,
			NORTH_NORTH_WEST,
			NORTH,
			NORTH_NORTH_EAST,
			NORTH_EAST,
			EAST_NORTH_EAST,
			EAST,
			EAST_SOUTH_EAST,
			SOUTH_EAST,
			SOUTH_SOUTH_EAST
	};
	
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
	
	/**
	 * Returns the rotation id
	 * @return rotation
	 */
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
	
	/**
	 * Returns how this face would modify the x coordinate
	 * @return modifier
	 */
	public int getModX() {
		return modX;
	}
	
	/**
	 * Returns how this face would modify the y coordinate
	 * @return modifier
	 */
	public int getModY() {
		return modY;
	}
	
	/**
	 * Returns how this face would modify the z coordinate
	 * @return modifier
	 */
	public int getModZ() {
		return modZ;
	}
	
	public <T extends Vector3d> T add(T loc) {
		loc.add(modX, modY, modZ);
		return loc;
	}
	
	public <T extends Vector3d> T sub(T loc) {
		loc.sub(modX, modY, modZ);
		return loc;
	}
	
	public <T extends Vector3d> T add(T loc, double mul) {
		loc.add(modX * mul, modY * mul, modZ * mul);
		return loc;
	}
	
	public <T extends Vector3d> T sub(T loc, double mul) {
		loc.sub(modX * mul, modY * mul, modZ * mul);
		return loc;
	}
	
	public <T extends Vector3i> T add(T loc) {
		loc.add(modX, modY, modZ);
		return loc;
	}
	
	public <T extends Vector3i> T sub(T loc) {
		loc.sub(modX, modY, modZ);
		return loc;
	}
	
	public <T extends Vector3i> T add(T loc, double mul) {
		loc.add((int) (modX * mul), (int) (modY * mul), (int) (modZ * mul));
		return loc;
	}
	
	public <T extends Vector3i> T sub(T loc, double mul) {
		loc.sub((int) (modX * mul), (int) (modY * mul), (int) (modZ * mul));
		return loc;
	}
	
	public <T extends Vector3f> T add(T loc) {
		loc.add(modX, modY, modZ);
		return loc;
	}
	
	public <T extends Vector3f> T sub(T loc) {
		loc.sub(modX, modY, modZ);
		return loc;
	}
	
	public <T extends Vector3f> T add(T loc, float mul) {
		loc.add(modX * mul, modY * mul, modZ * mul);
		return loc;
	}
	
	public <T extends Vector3f> T sub(T loc, float mul) {
		loc.sub(modX * mul, modY * mul, modZ * mul);
		return loc;
	}
	
	public static BlockFace getByRotation(int rotation) {
		return BY_ROTATION[rotation];
	}
	
	public static BlockFace getByFaceID(int faceID) {
		return BY_FACE_ID[faceID];
	}
	
	public int getFaceID() {
		return faceID;
	}

}
