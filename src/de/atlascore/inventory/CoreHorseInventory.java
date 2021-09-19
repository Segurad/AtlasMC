package de.atlascore.inventory;

import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.HorseInventory;
import de.atlasmc.inventory.InventoryHolder;

public class CoreHorseInventory extends CoreInventory implements HorseInventory {

	public CoreHorseInventory(ChatComponent title, InventoryHolder holder) {
		super(2, InventoryType.HORSE, title, holder);
	}

}
