package de.atlasmc.inventory;

/**
 * Represents all Furnace like Inventories 
 */
public interface AbstractFurnaceInventory extends AbstractCraftingInventory {
	
	ItemStack getFuel();
	
	void setFuel(ItemStack fuel);
	
	int getFuelLevel();
	
	void setFuelLevel(int value);
	
	int getMaxFuelLevel();
	
	void setMaxFuelLevel(int value);
	
	int getProgress();
	
	void setProgress(int value);
	
	int getMaxProgress();
	
	void setMaxProgress(int value);
	
}
