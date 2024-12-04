package de.atlasmc;

import java.util.List;

import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumValueCache;

public enum Difficulty implements EnumID, EnumValueCache {
	
	PEACEFUL,
	EASY,
	NORAML,
	HARD;
	
	private static List<Difficulty> VALUES;
	
	public int getID() {
		return ordinal();
	}
	
	public static Difficulty getByID(int id) {
		return getValues().get(id);
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<Difficulty> getValues() {
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
