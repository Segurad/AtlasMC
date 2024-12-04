package de.atlasmc.inventory.meta;

import java.util.List;

import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public interface GoatHornMeta extends ItemMeta {
	
	Instrument getInstrument();
	
	void setInstrument(Instrument instrument);
	
	GoatHornMeta clone();
	
	public static enum Instrument implements EnumName, EnumID, EnumValueCache {
		
		PONDER,
		SING,
		SEEK,
		FEEL,
		ADMIRE,
		CALL,
		YEARN,
		DREAM;
		
		private static List<Instrument> VALUES;
		
		private String name;
		
		private Instrument() {
			String name = "minecraft:" + name().toLowerCase() + "_goat_horn";
			this.name = name.intern();
		}
		
		@Override
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
		
		@Override
		public String getName() {
			return name;
		}
		
		public static Instrument getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<Instrument> instruments = getValues();
			final int size = instruments.size();
			for (int i = 0; i < size; i++) {
				Instrument instrument = instruments.get(i);
				if (instrument.name.equals(name)) {
					return instrument;
				}
			}
			return null;
		}
		
	}

}
