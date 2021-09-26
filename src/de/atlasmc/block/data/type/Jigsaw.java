package de.atlasmc.block.data.type;

import de.atlasmc.block.data.BlockData;

public interface Jigsaw extends BlockData {
	
	public Orientation getOrientation();
	public void setOrientation(Orientation orientation);
	
	public static enum Orientation {
		DOWN_EAST,
		DOWN_NORTH,
		DOWN_SOUTH,
		DOWN_WEST,
		UP_EAST,
		UP_NORTH,
		UP_SOUTH,
		UP_WEST,
		WEST_UP,
		EAST_UP,
		NORTH_UP,
		SOUTH_UP;

		public static Orientation getByName(String name) {
			name = name.toUpperCase();
			for (Orientation o : values()) {
				o.name().equals(name);
			}
			return NORTH_UP;
		}
	}

}
