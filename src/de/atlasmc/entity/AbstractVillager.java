package de.atlasmc.entity;

import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.MerchantInventory;

public interface AbstractVillager extends Breedable, InventoryHolder, Merchant {
	
	public int getHeadShakeTimer();
	
	public void setHeadShakeTimer(int time);
	
	public MerchantInventory getInventory();

	/**
	 * Sets the tick this Villager was last restocked
	 * @param tick
	 */
	public void setLastRestock(long tick);

	/**
	 * Returns the tick this Villager was last restocked
	 * @return
	 */
	public long getLastRestock();
	
	public int getRestocksToday();

	public void setRestocksToday(int restocks);
	
}
