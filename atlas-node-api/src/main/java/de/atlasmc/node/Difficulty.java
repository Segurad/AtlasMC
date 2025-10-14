package de.atlasmc.node;

import de.atlasmc.IDHolder;

public enum Difficulty implements IDHolder {
	
	PEACEFUL,
	EASY,
	NORAML,
	HARD;

	@Override
	public int getID() {
		return ordinal();
	}

}
