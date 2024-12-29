package de.atlasmc;

import java.util.List;

import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public enum Axis implements EnumName, EnumValueCache {
	
	X,
	Y,
	Z;

	private static List<Axis> VALUES;
	
	private final String name;
	
	private Axis() {
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
	public static Axis getByName(String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		List<Axis> values = getValues();
		final int size = values.size();
		for (int i = 0; i < size; i++) {
			Axis value = values.get(i);
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
	public static List<Axis> getValues() {
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
