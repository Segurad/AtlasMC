package de.atlasmc.core.node.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.InventoryType;
import de.atlasmc.node.inventory.SmokerInventory;

public class CoreSmokerInventory extends CoreAbstractFurnaceInventory implements SmokerInventory {

	public CoreSmokerInventory(Chat title, InventoryHolder holder) {
		super(InventoryType.SMOKER, title, holder);
	}

}
