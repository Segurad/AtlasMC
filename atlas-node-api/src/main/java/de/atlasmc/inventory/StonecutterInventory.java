package de.atlasmc.inventory;

public interface StonecutterInventory extends AbstractCraftingInventory {
	
	ItemStack getInput();
	
	void setInput(ItemStack input);
	
	int getSelectedRecipe();
	
	void setSelectedRecipe(int value);
	
}
