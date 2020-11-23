package de.atlasmc;

public enum Direction {

	NORTH(337.5f, 22.5f, 0),
	NORTH_EAST(22.5f, 67.5f, 1),
	EAST(67.5f, 112.5f, 2),
	SOUTH_EAST(112.5f, 157.5f, 3),
	SOUTH(157.5f, 202.5f, 4),
	SOUTH_WEST(202.5f, 247.5f, 5),
	WEST(247.5f, 292.5f, 6),
	NORTH_WEST(292.5f, 337.5f, 7);
	
	private final float min, max;
	private final int id;
	
	private Direction(float min, float max, int id) {
		this.max = max;
		this.min = min;
		this.id = id;
	}
	
	public static Direction getDirectionYaw(float yaw) {
		final float y = yaw + 180;
		for (final Direction d : values()) {
			if (y > d.min && y <= d.max) return d;
		}
		return NORTH;
	}
	
	public static Direction getDirectionDegree(float degree) {
		final float y = degree;
		for (final Direction d : values()) {
			if (y > d.min && y <= d.max) return d;
		}
		return NORTH;
	}
	
	public int getId() {
		return id;
	}
	
	public float getMax() {
		return max;
	}
	
	public float getMin() {
		return min;
	}
}
