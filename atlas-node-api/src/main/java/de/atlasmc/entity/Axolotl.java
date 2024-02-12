package de.atlasmc.entity;

import java.util.List;

public interface Axolotl extends Animal {
	
	Variant getVariant();
	
	void setVariant(Variant variant);
	
	boolean isFromBucket();
	
	void setFromBucket(boolean bucket);
	
	public static enum Variant {
		
		LUCY,
		WILD,
		GOLD,
		CYAN,
		BLUE;
		
		private static List<Variant> VALUES;
		
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
