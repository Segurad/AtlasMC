package de.atlasmc.block.data.type;

import java.util.List;

public interface BigDripleaf extends Dripleaf {
	
	Tilt getTilt();
	
	void setTilt(Tilt tilt);
	
	BigDripleaf clone();
	
	public static enum Tilt {
		
		NONE,
		UNSTABLE,
		PARTIAL,
		FULL;
		
		private static List<Tilt> VALUES;
		
		private String name;
		
		private Tilt() {
			this.name = name().toLowerCase();
		}
		
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
		
		public String getName() {
			return name;
		}
		
		public static Tilt getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			for (Tilt tilt : getValues()) {
				if (tilt.getName().equals(name)) {
					return tilt;
				}
			}
			throw new IllegalArgumentException("No value with found with name: " + name);
		}
		
	}

}
