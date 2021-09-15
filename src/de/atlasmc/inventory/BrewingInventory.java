package de.atlasmc.inventory;

public interface BrewingInventory extends Inventory {
	
	/**
	 * Returns the remaining fuel level
	 * @return fuel
	 */
	public int getFuelLevel();
	
	public void setFuelLevel(int value);
	
	public int getMaxFuelLevel();
	
	public void setMaxFuelLevel(int value);
	
	public int getDisplayFuel();
	
	/**
	 * Sets the displayed fuel value in range of 0 to 20<br>
	 * 0 equals empty<br>
	 * 20 equals full
	 * @param value the fuel value
	 */
	public void setDisplayFuel(int value);
	
	/**
	 * The time in ticks until the brewing cycle ends
	 * @return ticks
	 */
	public int getBrewTime();
	
	public void setBrewTime(int value);
	
	public int getDisplayBrewTime();
	
	/**
	 * Sets the displayed brewing cycle in range of 400 to 0<br>
	 * 400 equals started<br>
	 * 0 equals finished
	 * @param value of the brewing cycle
	 */
	public void setDisplayBrewTime(int value);
	
	public ItemStack getFuel();
	
	public void setFuel(ItemStack fuel);
	
	public ItemStack getIngredient();
	
	public void setIngredient(ItemStack ingredient);
	
}
