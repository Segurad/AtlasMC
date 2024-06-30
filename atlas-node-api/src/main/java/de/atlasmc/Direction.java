package de.atlasmc;

import java.util.List;

public enum Direction {

	NORTH(337.5f, 22.5f),
	NORTH_EAST(22.5f, 67.5f),
	EAST(67.5f, 112.5f),
	SOUTH_EAST(112.5f, 157.5f),
	SOUTH(157.5f, 202.5f),
	SOUTH_WEST(202.5f, 247.5f),
	WEST(247.5f, 292.5f),
	NORTH_WEST(292.5f, 337.5f);
	
	private static List<Direction> VALUES;
	
	private final float min;
	private final float max;
	
	private Direction(float min, float max) {
		this.max = max;
		this.min = min;
	}
	
	public static Direction getDirectionYaw(float yaw) {
		final float y = yaw + 180;
		for (final Direction d : getValues()) {
			if (y > d.min && y <= d.max) return d;
		}
		return NORTH;
	}
	
	public static Direction getDirectionDegree(float degree) {
		final float y = degree;
		for (final Direction d : getValues()) {
			if (y > d.min && y <= d.max) return d;
		}
		return NORTH;
	}
	
	public int getID() {
		return ordinal();
	}
	
	public static Direction getByID(int id) {
		return getValues().get(id);
	}
	
	public float getMax() {
		return max;
	}
	
	public float getMin() {
		return min;
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<Direction> getValues() {
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
