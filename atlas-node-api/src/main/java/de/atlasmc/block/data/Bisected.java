package de.atlasmc.block.data;

import java.util.List;

import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public interface Bisected extends BlockData {
	
	Half getHalf();
	
	void setHalf(Half half);
	
	Bisected clone();
	
	public static enum Half implements EnumName, EnumValueCache {
		TOP(0),
		UPPER(0),
		BOTTOM(1),
		LOWER(1);

		private static List<Half> VALUES;
		
		private int id;
		private String name;
		
		private Half(int id) {
			this.id = id;
			name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		public static Half getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<Half> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				Half value = values.get(i);
				if (value.name.equals(name))
					return value;
			}
			return null;
		}
		
		public int getID() {
			return id;
		}
		
		public static Half getByID(int id) {
			return getValues().get(id*2);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Half> getValues() {
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
