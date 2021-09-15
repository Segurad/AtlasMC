package de.atlasmc.inventory;

public interface StonecutterInventory extends Inventory {
	
	public ItemStack getResult();
	
	public void setResult(ItemStack result);
	
	public ItemStack getInput();
	
	public void setInput(ItemStack input);
	
	public int getSelectedRecipe();
	
	public void setSelectedRecipe(int value);
	
}
