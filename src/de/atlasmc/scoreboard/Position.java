package de.atlasmc.scoreboard;

public enum Position {
	
	LIST,
	SIDEBAR,
	BELOW_NAME;
	
	public int getID() {
		return ordinal();
	}
	
	public static Position getByID(int id) {
		return values()[id];
	}

}
