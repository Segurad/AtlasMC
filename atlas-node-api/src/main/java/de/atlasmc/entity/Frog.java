package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public interface Frog extends Animal {

	Variant getVariant();
	
	void setVariant(Variant variant);
	
	Entity getTongueTarget();
	
	void setTangueTarget(Entity entity);
	
	public static enum Variant implements EnumID, EnumName, EnumValueCache {
		TEMPERATE,
		WARM,
		COLD;
		
		private static List<Variant> VALUES;
		
		private String name;
		
		private Variant() {
			String name = "minecraft:" + name().toLowerCase();
			this.name = name.intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		public static Variant getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<Variant> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				Variant value = values.get(i);
				if (value.name.equals(name))
					return value;
			}
			return null;
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		public static Variant getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Variant> getValues() {
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
