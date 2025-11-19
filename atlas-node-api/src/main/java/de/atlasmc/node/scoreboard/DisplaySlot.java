package de.atlasmc.node.scoreboard;

import de.atlasmc.IDHolder;

public enum DisplaySlot implements IDHolder {
	
	LIST,
	SIDEBAR,
	BELOW_NAME;

	@Override
	public int getID() {
		return ordinal();
	}

}
