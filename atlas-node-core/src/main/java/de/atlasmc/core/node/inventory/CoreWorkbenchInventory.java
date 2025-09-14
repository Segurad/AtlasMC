package de.atlasmc.core.node.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.InventoryType;
import de.atlasmc.node.inventory.WorkbenchInventory;

public class CoreWorkbenchInventory extends CoreInventory implements WorkbenchInventory {

	public CoreWorkbenchInventory(Chat title, InventoryHolder holder) {
		super(10, InventoryType.WORKBENCH, title, holder);
	}

}
