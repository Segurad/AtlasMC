package de.atlasmc.block.data.type;

import java.util.List;
import java.util.Set;

import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.AnaloguePowerable;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public interface RedstoneWire extends AnaloguePowerable {
	
	Set<BlockFace> getAllowedFaces();
	
	Connection getFace(BlockFace face);
	
	void setFace(BlockFace face, Connection connection);
	
	public static enum Connection implements EnumName, EnumValueCache {
		UP,
		SIDE,
		NONE;

		private static List<Connection> VALUES;
		
		private String name;
		
		private Connection() {
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
		public static Connection getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<Connection> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				Connection value = values.get(i);
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
		public static List<Connection> getValues() {
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
