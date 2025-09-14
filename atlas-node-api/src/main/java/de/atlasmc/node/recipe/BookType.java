package de.atlasmc.node.recipe;

import java.util.List;

public enum BookType {

	CRAFTING,
	FURNACE,
	BLAST_FURNACE,
	SMOKER;
	
	private static List<BookType> VALUES;
	
	public int getID() {
		return ordinal();
	}
	
	public static BookType getByID(int id) {
		return getValues().get(id);
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<BookType> getValues() {
		if (VALUES == null)
			VALUES = List.of(values());
		return VALUES;
	}
	
	/**
	 * Releases the system resources used from the values cache
	 */
	public static void freeValues() {
		VALUES = null;
	}
	
}
