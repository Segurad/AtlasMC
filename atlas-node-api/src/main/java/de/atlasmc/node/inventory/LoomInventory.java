package de.atlasmc.node.inventory;

import de.atlasmc.node.block.tile.Banner.EnumPatternType;

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
