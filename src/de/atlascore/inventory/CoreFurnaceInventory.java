package de.atlascore.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.FurnaceInventory;
import de.atlasmc.inventory.InventoryHolder;

public class CoreFurnaceInventory extends CoreAbstractFurnaceInventory implements FurnaceInventory {
	
	public CoreFurnaceInventory(Chat title, InventoryHolder holder) {
		super(InventoryType.FURNACE, title, holder);
	}

}
