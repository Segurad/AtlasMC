package de.atlasmc.node.recipe;

import java.util.List;

public enum RecipeCategory {
	
	BUILDING,
	REDSTONE,
	EQUIPMENT,
	MISC,
	COOKING_FOOD,
	COOKING_BLOCKS,
	COOKING_MISC;
	
	
	private static List<RecipeCategory> VALUES;
	
	public int getID() {
		return ordinal();
	}
	
	public static RecipeCategory getByID(int id) {
		return getValues().get(id);
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<RecipeCategory> getValues() {
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
