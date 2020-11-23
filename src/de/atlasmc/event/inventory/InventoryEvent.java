package de.atlasmc.event.inventory;

import de.atlasmc.event.Event;
import de.atlasmc.inventory.Inventory;

public class InventoryEvent extends Event {
	
	private final Inventory inv;
	
	public InventoryEvent(Inventory inv) {
		this.inv = inv;
	}
	
	public Inventory getInventory() {
		return inv;
	}

}
