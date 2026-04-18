package de.atlasmc.node.recipe;

import de.atlasmc.IDHolder;

public enum RecipeCategory implements IDHolder {
	
	BUILDING,
	REDSTONE,
	EQUIPMENT,
	MISC,
	COOKING_FOOD,
	COOKING_BLOCKS,
	COOKING_MISC;
	
	public int getID() {
		return ordinal();
	}
	
}
