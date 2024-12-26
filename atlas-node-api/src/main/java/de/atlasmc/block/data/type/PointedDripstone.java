package de.atlasmc.block.data.type;

import java.util.List;

import de.atlasmc.block.data.Waterlogged;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public interface PointedDripstone extends Waterlogged {
	
	Thickness getThickness();
	
	void setThickness(Thickness thickness);
	
	VerticalDirection getDirection();
	
	void setDirection(VerticalDirection direction);
	
	PointedDripstone clone();
	
	public static enum Thickness implements EnumName, EnumValueCache {
		
		TIP_MERGE,
		TIP,
		FRUSTUM,
		MIDDLE,
		BASE;

		private static List<Thickness> VALUES;
		
		private final String name;
		
		private Thickness() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		public static Thickness getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<Thickness> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				Thickness value = values.get(i);
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
		public static List<Thickness> getValues() {
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
	
	public static enum VerticalDirection {
		
		UP,
		DOWN;

		private static List<VerticalDirection> VALUES;
		
		private final String name;
		
		private VerticalDirection() {
			this.name = name().toLowerCase().intern();
		}
		
		public String getName() {
			return name;
		}
		
		/**
		 * Returns the value represented by the name or null if no matching value has been found
		 * @param name the name of the value
		 * @return value or null
		 */
		public static VerticalDirection getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<VerticalDirection> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				VerticalDirection value = values.get(i);
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
		public static List<VerticalDirection> getValues() {
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
