package de.atlascore.inventory;

import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.FurnaceInventory;
import de.atlasmc.inventory.InventoryHolder;

public class CoreFurnaceInventory extends CoreInventory implements FurnaceInventory {
	
	public CoreFurnaceInventory(String title, InventoryHolder holder) {
		super(3, InventoryType.FURNACE, title, holder);
	}

}
