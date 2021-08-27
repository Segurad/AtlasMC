package de.atlasmc.entity;

import de.atlasmc.inventory.InventoryHolder;

public interface AbstractVillager extends Breedable, InventoryHolder, Merchant {
	
	public int getHeadShakeTimer();

}
