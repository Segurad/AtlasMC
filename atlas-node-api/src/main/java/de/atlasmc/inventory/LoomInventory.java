package de.atlasmc.inventory;

import de.atlasmc.block.tile.Banner.PatternType;

public interface LoomInventory extends AbstractCraftingInventory {
	
	ItemStack getBanner();
	
	ItemStack getDye();
	
	ItemStack getPatternItem();
	
	void setBanner(ItemStack banner);
	
	void setDye(ItemStack dye);
	
	void setPatternItem(ItemStack pattern);
	
	PatternType getPattern();
	
	void setPattern(PatternType pattern);

}
