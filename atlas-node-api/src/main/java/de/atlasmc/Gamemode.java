package de.atlasmc;

import java.util.List;

import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumValueCache;

public enum Gamemode implements EnumID, EnumValueCache {
	
	SURVIVAL,
	CREATIVE,
	ADVENTURE,
	SPECTATOR;
	
	private static List<Gamemode> VALUES;
	
	public int getID() {
		return ordinal();
	}
	
	public static Gamemode getByID(int id) {
		return getValues().get(id);
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<Gamemode> getValues() {
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
