package de.atlasmc;

import java.util.List;

public enum Instrument {
	
	HARP,
	BASEDRUM,
	SNARE,
	HAT,
	BASS,
	FLUTE,
	BELL,
	GUITAR,
	CHIME,
	XYLOPHONE,
	IRON_XYLOPHONE,
	COW_BELL,
	DIDGERIDOO,
	BIT,
	BANJO,
	PLING;

	private static List<Instrument> VALUES;
	
	public static Instrument getByName(String name) {
		for (Instrument i : getValues()) {
			if (i.name().equalsIgnoreCase(name)) return i;
		}
		return HARP;
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

}
