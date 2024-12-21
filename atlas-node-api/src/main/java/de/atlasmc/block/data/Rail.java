package de.atlasmc.block.data;

import java.util.List;
import java.util.Set;

import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public interface Rail extends Waterlogged {
	
	Shape getShape();
	
	void setShape(Shape shape);
	
	Set<Shape> getShapes();
	
	public static enum Shape implements EnumName, EnumValueCache {
		NORTH_SOUTH,
		EAST_WEST,
		ASCENDING_EAST,
		ASCENDING_WEST,
		ASCENDING_NORTH,
		ASCENTING_SOUTH,
		SOUT_EAST,
		SOUTH_WEST,
		NORTH_WEST,
		NORTH_EAST;

		private static List<Shape> VALUES;
		
		private String name;
		
		private Shape() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		/**
		 * Returns the value represented by the name or null if no matching value has been found
		 * @param name the name of the value
		 * @return value or null
		 */
		public static Shape getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<Shape> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				Shape value = values.get(i);
				if (value.name.equals(name)) 
					return value;
			}
			return null;
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Shape> getValues() {
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

}
