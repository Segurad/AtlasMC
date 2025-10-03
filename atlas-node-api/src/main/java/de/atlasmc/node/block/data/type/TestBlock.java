package de.atlasmc.node.block.data.type;

import java.util.List;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public interface TestBlock extends BlockData {
	
	Mode getMode();
	
	void setMode(Mode mode);
	
	public static enum Mode implements EnumName, EnumValueCache {
		START,
		LOG,
		FAIL,
		ACCEPT;

		private static List<Mode> VALUES;
		
		private String name;
		
		private Mode() {
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
		public static Mode getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<Mode> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				Mode value = values.get(i);
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
		public static List<Mode> getValues() {
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
