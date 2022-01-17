package de.atlasmc;

import java.util.List;

public enum SoundCategory {
	
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
	
}
