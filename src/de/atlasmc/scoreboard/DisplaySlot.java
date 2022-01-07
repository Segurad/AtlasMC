package de.atlasmc.scoreboard;

public enum DisplaySlot {
	
	LIST,
	SIDEBAR,
	BELOW_NAME;
	
	public int getID() {
		return ordinal();
	}
	
	public static DisplaySlot getByID(int id) {
		return values()[id];
	}

}
