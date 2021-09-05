package de.atlasmc.entity;

import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.PlayerInventory;

public interface HumanEntity extends LivingEntity, InventoryHolder {
	
	public PlayerInventory getInventory();

}
