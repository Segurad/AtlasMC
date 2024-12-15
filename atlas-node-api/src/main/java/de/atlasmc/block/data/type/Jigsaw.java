package de.atlasmc.block.data.type;

import java.util.List;

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
		
		private static List<Orientation> VALUES;
		
		private final String name;
		
		private Orientation() {
			this.name = name().toLowerCase();
		}
		
		public String getNameID() {
			return name;
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Orientation> getValues() {
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

		public static Orientation getByName(String name) {
			name = name.toUpperCase();
			for (Orientation o : values()) {
				o.name().equals(name);
			}
			return NORTH_UP;
		}
	}

}
