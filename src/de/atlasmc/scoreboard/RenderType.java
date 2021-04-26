package de.atlasmc.scoreboard;

public enum RenderType {
	
	INTEGER,
	HEARTS;
	
	public static RenderType getByID(int id) {
		return values()[id];
	}

}
