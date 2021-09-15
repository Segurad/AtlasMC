package de.atlasmc.inventory;

import de.atlasmc.block.tile.Banner.Pattern;

public interface LoomInventory extends Inventory {
	
	public ItemStack getBanner();
	
	public ItemStack getDye();
	
	public ItemStack getPatternItem();
	
	public void setBanner(ItemStack banner);
	
	public void setDye(ItemStack dye);
	
	public void setPatternItem(ItemStack pattern);
	
	public ItemStack getResult();
	
	public void setResult(ItemStack result);
	
	public Pattern getPattern();
	
	public void setPattern(Pattern pattern);

}
