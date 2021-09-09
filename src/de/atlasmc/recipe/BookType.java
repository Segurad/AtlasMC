package de.atlasmc.recipe;

public enum BookType {

	CRAFTING,
	FURNACE,
	BLAST_FURNACE,
	SMOKER;
	
	public static BookType getByID(int id) {
		return values()[id];
	}
	
}
