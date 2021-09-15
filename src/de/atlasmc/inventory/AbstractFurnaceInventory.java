package de.atlasmc.inventory;

/**
 * Represents all Furnace like Inventories 
 */
public interface AbstractFurnaceInventory extends Inventory {
	
	public ItemStack getFuel();
	
	public void setFuel(ItemStack fuel);
	
	public ItemStack getResult();
	
	public void setResult(ItemStack result);
	
	public int getFuelLevel();
	
	public void setFuelLevel(int value);
	
	public int getMaxFuelLevel();
	
	public void setMaxFuelLevel(int value);
	
	public int getProgress();
	
	public void setProgress(int value);
	
	public int getMaxProgress();
	
	public void setMaxProgress(int value);
	
}
