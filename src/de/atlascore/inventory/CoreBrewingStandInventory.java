package de.atlascore.inventory;

import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.BrewingStandInventory;
import de.atlasmc.inventory.InventoryHolder;

public class CoreBrewingStandInventory extends CoreInventory implements BrewingStandInventory {

	public CoreBrewingStandInventory(String title, InventoryHolder holder) {
		super(5, InventoryType.BREWING, title, holder);
	}

}
