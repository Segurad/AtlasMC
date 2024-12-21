package de.atlasmc.block.data.type;

import java.util.List;

import de.atlasmc.block.data.Directional;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public interface Bed extends Directional {
	
	boolean isOccupied();
	
	void setOccupied(boolean occupied);
	
	Part getPart();
	
	void setPart(Part part);
	
	public static enum Part implements EnumName, EnumValueCache {
		
		HEAD,
		FOOT;

		private static List<Part> VALUES;
		
		private String name;
		
		private Part() {
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
		public static Part getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<Part> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				Part a = values.get(i);
				if (a.name.equals(name)) 
					return a;
			}
			return null;
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Part> getValues() {
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
