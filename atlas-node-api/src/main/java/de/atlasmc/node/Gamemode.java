package de.atlasmc.node;

import de.atlasmc.IDHolder;

public enum Gamemode implements IDHolder {
	
	SURVIVAL,
	CREATIVE,
	ADVENTURE,
	SPECTATOR;
	
	@Override
	public int getID() {
		return ordinal();
	}

}
