package de.atlasmc.entity;

import java.util.List;

public interface Frog extends Animal {

	Variant getVariant();
	
	void setVariant(Variant variant);
	
	Entity getTongueTarget();
	
	void setTangueTarget(Entity entity);
	
	public static enum Variant {
		TEMPERATE,
		WARM,
		COLD;
		
		private static List<Variant> VALUES;
		
		private String nameID;
		
		private Variant() {
			nameID = "minecraft:" + name().toLowerCase();
		}
		
		public String getNameID() {
			return nameID;
		}
		
		public static Variant getByNameID(String nameID) {
			if (nameID == null)
				throw new IllegalArgumentException("NameID can not be null!");
			for (Variant value : getValues()) {
				if (value.getNameID().equals(nameID))
					return value;
			}
			throw new IllegalArgumentException("No value with name found: " + nameID);
		}
		
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
