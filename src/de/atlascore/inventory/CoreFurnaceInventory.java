package de.atlascore.inventory;

import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.FurnaceInventory;
import de.atlasmc.inventory.InventoryHolder;

public class CoreFurnaceInventory extends CoreAbstractFurnaceInventory implements FurnaceInventory {
	
	public CoreFurnaceInventory(String title, InventoryHolder holder) {
		super(InventoryType.FURNACE, title, holder);
	}

}
