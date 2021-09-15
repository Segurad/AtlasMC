package de.atlascore.inventory;

import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.WorkbenchInventory;

public class CoreWorkbenchInventory extends CoreInventory implements WorkbenchInventory {

	public CoreWorkbenchInventory(String title, InventoryHolder holder) {
		super(10, InventoryType.WORKBENCH, title, holder);
	}

}
