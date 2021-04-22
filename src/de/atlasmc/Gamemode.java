package de.atlasmc;

public enum Gamemode {
	
	SURVIVAL,
	CREATIVE,
	ADVENTURE,
	SPECTATOR;
	
	public static Gamemode getByID(int id) {
		return values()[id];
	}

}
