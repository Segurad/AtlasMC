package de.atlasmc.inventory;

public interface BrewingInventory extends Inventory {
	
	/**
	 * Returns the remaining fuel level
	 * @return fuel
	 */
	int getFuelLevel();
	
	void setFuelLevel(int value);
	
	int getMaxFuelLevel();
	
	void setMaxFuelLevel(int value);
	
	int getDisplayFuel();
	
	/**
	 * Sets the displayed fuel value in range of 0 to 20<br>
	 * 0 equals empty<br>
	 * 20 equals full
	 * @param value the fuel value
	 */
	void setDisplayFuel(int value);
	
	/**
	 * The time in ticks until the brewing cycle ends
	 * @return ticks
	 */
	int getBrewTime();
	
	void setBrewTime(int value);
	
	int getDisplayBrewTime();
	
	/**
	 * Sets the displayed brewing cycle in range of 400 to 0<br>
	 * 400 equals started<br>
	 * 0 equals finished
	 * @param value of the brewing cycle
	 */
	void setDisplayBrewTime(int value);
	
	ItemStack getFuel();
	
	void setFuel(ItemStack fuel);
	
	ItemStack getIngredient();
	
	void setIngredient(ItemStack ingredient);
	
}
