package de.atlasmc.entity;

import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.MerchantInventory;

public interface AbstractVillager extends Breedable, InventoryHolder, Merchant {
	
	public int getHeadShakeTimer();
	
	public void setHeadShakeTimer(int time);
	
	public MerchantInventory getInventory();

}
