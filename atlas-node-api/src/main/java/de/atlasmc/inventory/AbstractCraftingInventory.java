package de.atlasmc.inventory;

public interface AbstractCraftingInventory extends Inventory {
	
	ItemStack getResult();
	
	void setResult(ItemStack result);

}
