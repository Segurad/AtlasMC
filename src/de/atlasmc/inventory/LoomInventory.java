package de.atlasmc.inventory;

import de.atlasmc.block.tile.Banner.PatternType;

public interface LoomInventory extends Inventory {
	
	public ItemStack getBanner();
	
	public ItemStack getDye();
	
	public ItemStack getPatternItem();
	
	public void setBanner(ItemStack banner);
	
	public void setDye(ItemStack dye);
	
	public void setPatternItem(ItemStack pattern);
	
	public ItemStack getResult();
	
	public void setResult(ItemStack result);
	
	public PatternType getPattern();
	
	public void setPattern(PatternType pattern);

}
