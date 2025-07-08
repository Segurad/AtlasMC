package de.atlasmc.inventory;

public interface BrewingInventory extends Inventory {
	
	/**
	 * Returns the remaining fuel level
	 * @return fuel
	 */
	int getFuelLevel();
	
	void setFuelLevel(int value);
	
	/**
	 * The time in ticks until the brewing cycle ends
	 * @return ticks
	 */
	int getBrewTime();
	
	void setBrewTime(int value);
	
	ItemStack getFuel();
	
	void setFuel(ItemStack fuel);
	
	ItemStack getIngredient();
	
	void setIngredient(ItemStack ingredient);
	
}
