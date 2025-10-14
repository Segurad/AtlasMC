package de.atlasmc.node;

import java.util.List;

import de.atlasmc.IDHolder;
import de.atlasmc.util.EnumUtil;

public enum Direction implements IDHolder {

	NORTH(337.5f, 22.5f),
	NORTH_EAST(22.5f, 67.5f),
	EAST(67.5f, 112.5f),
	SOUTH_EAST(112.5f, 157.5f),
	SOUTH(157.5f, 202.5f),
	SOUTH_WEST(202.5f, 247.5f),
	WEST(247.5f, 292.5f),
	NORTH_WEST(292.5f, 337.5f);
	
	private final float min;
	private final float max;
	
	private Direction(float min, float max) {
		this.max = max;
		this.min = min;
	}
	
	public static Direction getDirectionYaw(float yaw) {
		final float y = yaw + 180;
		List<Direction> values = EnumUtil.getValues(Direction.class);
		final int size = values.size();
		for (int i = 0; i < size; i++) {
			Direction d = values.get(i);
			if (y > d.min && y <= d.max) 
				return d;
		}
		return NORTH;
	}
	
	public static Direction getDirectionDegree(float degree) {
		final float y = degree;
		List<Direction> values = EnumUtil.getValues(Direction.class);
		final int size = values.size();
		for (int i = 0; i < size; i++) {
			Direction d = values.get(i);
			if (y > d.min && y <= d.max) 
				return d;
		}
		return NORTH;
	}
	
	@Override
	public int getID() {
		return ordinal();
	}
	
	public float getMax() {
		return max;
	}
	
	public float getMin() {
		return min;
	}
	
}
