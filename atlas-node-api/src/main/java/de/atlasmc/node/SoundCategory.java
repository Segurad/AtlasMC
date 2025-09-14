package de.atlasmc.node;

import java.util.List;

import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumValueCache;

public enum SoundCategory implements EnumID, EnumValueCache {
	
	MASTER,
	MUSIC,
	RECORDS,
	WEATHER,
	BLOCKS,
	HOSTILE,
	NEUTRAL,
	PLAAYERS,
	AMBIENT,
	VOICE;
	
	private static List<SoundCategory> VALUES;

	public static SoundCategory getByID(int id) {
		return getValues().get(id);
	}

	@Override
	public int getID() {
		return ordinal();
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<SoundCategory> getValues() {
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
