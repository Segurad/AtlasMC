package de.atlasmc.scoreboard;

public enum TeamOptionType {
	
	ALWAYS,
	FOR_OTHER_TEAMS,
	FOR_OWN_TEAM,
	NEVER;
	
	public static TeamOptionType getByID(int id) {
		return values()[id];
	}
	
}