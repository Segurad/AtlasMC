package de.atlasmc;

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

	public static SoundCategory getByID(int id) {
		SoundCategory[] values = values();
		return values[id];
	}
}
