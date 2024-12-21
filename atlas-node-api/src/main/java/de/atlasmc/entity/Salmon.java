package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public interface Salmon extends Fish {

	Type getSalmonType();
	
	void setSalmonType(Type type);
	
	public static enum Type implements EnumName, EnumValueCache {
		SMALL,
		MEDIUM,
		LARGE;
		
		private static List<Type> VALUES;
		
		private final String name;
		
		private Type() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		public static Type getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<Type> types = getValues();
			final int size = types.size();
			for (int i = 0; i < size; i++) {
				Type type = types.get(i);
				if (type.name.equals(name))
					return type;
			}
			return null;
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Type> getValues() {
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
