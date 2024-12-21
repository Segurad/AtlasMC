package de.atlasmc.block.data.type;

import java.util.List;

import de.atlasmc.block.data.Bisected;
import de.atlasmc.block.data.Directional;
import de.atlasmc.block.data.Openable;
import de.atlasmc.block.data.Powerable;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public interface Door extends Bisected, Directional, Openable, Powerable {
	
	Hinge getHinge();
	
	void setHinge(Hinge hinge);
	
	public static enum Hinge implements EnumName, EnumValueCache {
		LEFT,
		RIGHT;

		private static List<Hinge> VALUES;
		
		private String name;
		
		private Hinge() {
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
		public static Hinge getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<Hinge> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				Hinge value = values.get(i);
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
		public static List<Hinge> getValues() {
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
