package de.atlasmc.node.block.data.type;

import java.util.List;

import de.atlasmc.node.block.data.Bisected;
import de.atlasmc.node.block.data.Directional;
import de.atlasmc.node.block.data.Waterlogged;
import de.atlasmc.util.AtlasEnum;

public interface Stairs extends Bisected, Directional, Waterlogged {
	
	Shape getShape();
	
	void setShape(Shape shape);
	
	Stairs clone();
	
	public static enum Shape implements AtlasEnum {
		STRAIGHT,
		INNER_LEFT,
		INNER_RIGHT,
		OUTER_LEFT,
		OUTER_RIGHT;

		private static List<Shape> VALUES;
		
		private String name;
		
		private Shape() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public int getID() {
			return ordinal();
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
			final List<Shape> values = getValues();
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
