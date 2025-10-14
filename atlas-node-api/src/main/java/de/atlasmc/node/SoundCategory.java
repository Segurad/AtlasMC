package de.atlasmc.node;

import de.atlasmc.IDHolder;

public enum SoundCategory implements IDHolder {
	
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

	@Override
	public int getID() {
		return ordinal();
	}
	
}
