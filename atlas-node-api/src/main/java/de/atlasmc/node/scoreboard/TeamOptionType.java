package de.atlasmc.node.scoreboard;

import de.atlasmc.IDHolder;

public enum TeamOptionType implements IDHolder {
	
	ALWAYS,
	FOR_OTHER_TEAMS,
	FOR_OWN_TEAM,
	NEVER;
	
	@Override
	public int getID() {
		return ordinal();
	}
	
}