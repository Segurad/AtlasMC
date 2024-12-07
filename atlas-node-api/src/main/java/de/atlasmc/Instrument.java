package de.atlasmc;

import java.util.List;

import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public enum Instrument implements EnumName, EnumValueCache {
	
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
	
	private String name;
	
	private Instrument() {
		this.name = name().toLowerCase().intern();
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public static Instrument getByName(String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		List<Instrument> values = getValues();
		final int size = values.size();
		for (int i = 0; i < size; i++) {
			Instrument value = values.get(i);
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
