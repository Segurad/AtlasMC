package de.atlascore.inventory;

import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.SmokerInventory;

public class CoreSmokerInventory extends CoreAbstractFurnaceInventory implements SmokerInventory {

	public CoreSmokerInventory(ChatComponent title, InventoryHolder holder) {
		super(InventoryType.SMOKER, title, holder);
	}

}
