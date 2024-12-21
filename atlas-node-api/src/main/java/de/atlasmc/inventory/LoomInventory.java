package de.atlasmc.inventory;

import de.atlasmc.block.tile.Banner.EnumPatternType;

public interface LoomInventory extends AbstractCraftingInventory {
	
	ItemStack getBanner();
	
	ItemStack getDye();
	
	ItemStack getPatternItem();
	
	void setBanner(ItemStack banner);
	
	void setDye(ItemStack dye);
	
	void setPatternItem(ItemStack pattern);
	
	EnumPatternType getPattern();
	
	void setPattern(EnumPatternType pattern);

}
