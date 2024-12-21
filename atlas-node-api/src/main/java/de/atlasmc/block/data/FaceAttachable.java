package de.atlasmc.block.data;

import java.util.List;

import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public interface FaceAttachable extends BlockData {

	AttachedFace getAttachedFace();
	
	void setAttachedFace(AttachedFace face);
	
	public static enum AttachedFace implements EnumName, EnumValueCache {
		FLOOR,
		WALL,
		CEILING;

		private static List<AttachedFace> VALUES;
		
		private String name;
		
		private AttachedFace() {
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
		public static AttachedFace getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<AttachedFace> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				AttachedFace a = values.get(i);
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
		public static List<AttachedFace> getValues() {
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
