package de.atlasmc.inventory.meta;

import java.util.List;

public interface GoatHornMeta extends ItemMeta {
	
	Instrument getInstrument();
	
	void setInstrument(Instrument instrument);
	
	GoatHornMeta clone();
	
	public static enum Instrument {
		
		PONDER,
		SING,
		SEEK,
		FEEL,
		ADMIRE,
		CALL,
		YEARN,
		DREAM;
		
		private static List<Instrument> VALUES;
		
		private String nameID;
		
		private Instrument() {
			this.nameID = "minecraft:" + name().toLowerCase() + "_goat_horn";
		}
		
		public int getID() {
			return ordinal();
		}
		
		public static Instrument getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Instrument> getValues() {
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
			return nameID;
		}
		
		public static Instrument getByNameID(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			for (Instrument tilt : getValues()) {
				if (tilt.getName().equals(name)) {
					return tilt;
				}
			}
			throw new IllegalArgumentException("No value found with name: " + name);
		}
		
	}

}
