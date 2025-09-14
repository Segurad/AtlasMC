package de.atlasmc.node.block.data.type;

import java.util.List;

import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public interface BigDripleaf extends Dripleaf {
	
	Tilt getTilt();
	
	void setTilt(Tilt tilt);
	
	BigDripleaf clone();
	
	public static enum Tilt implements EnumID, EnumName, EnumValueCache {
		
		NONE,
		UNSTABLE,
		PARTIAL,
		FULL;
		
		private static List<Tilt> VALUES;
		
		private String name;
		
		private Tilt() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		public static Tilt getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Tilt> getValues() {
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
		
		@Override
		public String getName() {
			return name;
		}
		
		public static Tilt getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<Tilt> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				Tilt tilt = values.get(i);
				if (tilt.name.equals(name)) {
					return tilt;
				}
			}
			return null;
		}
		
	}

}
