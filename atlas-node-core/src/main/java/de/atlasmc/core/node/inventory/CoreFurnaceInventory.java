package de.atlasmc.core.node.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.node.inventory.FurnaceInventory;
import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.InventoryType;

public class CoreFurnaceInventory extends CoreAbstractFurnaceInventory implements FurnaceInventory {
	
	public CoreFurnaceInventory(Chat title, InventoryHolder holder) {
		super(InventoryType.FURNACE, title, holder);
	}

}
