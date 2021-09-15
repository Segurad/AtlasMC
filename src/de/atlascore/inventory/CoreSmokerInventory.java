package de.atlascore.inventory;

import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.SmokerInventory;

public class CoreSmokerInventory extends CoreAbstractFurnaceInventory implements SmokerInventory {

	public CoreSmokerInventory(String title, InventoryHolder holder) {
		super(InventoryType.SMOKER, title, holder);
	}

}
