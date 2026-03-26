package de.atlasmc.node.recipe;

import de.atlasmc.IDHolder;

public enum BookType implements IDHolder {

	CRAFTING,
	FURNACE,
	BLAST_FURNACE,
	SMOKER;
	
	@Override
	public int getID() {
		return ordinal();
	}
	
}
