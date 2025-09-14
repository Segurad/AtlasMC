package de.atlasmc.node.block.data;

import java.util.List;
import java.util.Set;

import de.atlasmc.node.block.BlockFace;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public interface HightConnectable extends BlockData {
	
	Height getHeight(BlockFace face);
	
	void setHeight(BlockFace face, Height height);
	
	Set<BlockFace> getAllowedFaces();
	
	public static enum Height implements EnumName, EnumValueCache {
		NONE,
		LOW,
		TALL;

		private static List<Height> VALUES;
		
		private String name;
		
		private Height() {
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
		public static Height getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<Height> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				Height value = values.get(i);
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
		public static List<Height> getValues() {
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
