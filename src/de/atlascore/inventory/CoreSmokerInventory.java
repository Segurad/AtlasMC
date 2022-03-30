package de.atlascore.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.SmokerInventory;

public class CoreSmokerInventory extends CoreAbstractFurnaceInventory implements SmokerInventory {

	public CoreSmokerInventory(Chat title, InventoryHolder holder) {
		super(InventoryType.SMOKER, title, holder);
	}

}
