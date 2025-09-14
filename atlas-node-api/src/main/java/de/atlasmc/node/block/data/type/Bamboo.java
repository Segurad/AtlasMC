package de.atlasmc.node.block.data.type;

import java.util.List;

import de.atlasmc.node.block.data.Ageable;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public interface Bamboo extends Ageable, Sapling {

	public Leaves getLeaves();
	public void setLeaves(Leaves leaves);
	
	public static enum Leaves implements EnumName, EnumValueCache {
		NONE,
		SMALL,
		LARGE;
		
		private static List<Leaves> VALUES;
		
		private String name;
		
		private Leaves() {
			this.name = name().toLowerCase().intern();
		}

		@Override
		public String getName() {
			return name;
		}

		public static Leaves getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<Leaves> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				Leaves value = values.get(i);
				if (value.name.equals(name)) {
					return value;
				};
			}
			return null;
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Leaves> getValues() {
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
