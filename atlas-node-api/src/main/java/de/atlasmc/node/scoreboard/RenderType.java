package de.atlasmc.node.scoreboard;

import de.atlasmc.IDHolder;

public enum RenderType implements IDHolder {
	
	INTEGER,
	HEARTS;
	
	@Override
	public int getID() {
		return ordinal();
	}

}
