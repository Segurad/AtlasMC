package de.atlasmc.node.block.data;

import java.util.List;

public interface Orientable extends BlockData {
	
	Orientation getOrientation();
	
	void setOrientation(Orientation orientation);
	
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
			this.name = name().toLowerCase().intern();
		}
		
		public String getName() {
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

		/**
		 * Returns the value represented by the name or null if no matching value has been found
		 * @param name the name of the value
		 * @return value or null
		 */
		public static Orientation getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<Orientation> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				Orientation value = values.get(i);
				if (value.name.equals(name)) 
					return value;
			}
			return null;
		}
		
	}

}
