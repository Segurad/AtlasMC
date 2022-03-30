package de.atlascore.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.HorseInventory;
import de.atlasmc.inventory.InventoryHolder;

public class CoreHorseInventory extends CoreInventory implements HorseInventory {

	public CoreHorseInventory(Chat title, InventoryHolder holder) {
		super(2, InventoryType.HORSE, title, holder);
	}

}
