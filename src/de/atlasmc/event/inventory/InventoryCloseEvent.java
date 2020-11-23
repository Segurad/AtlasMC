package de.atlasmc.event.inventory;

import de.atlasmc.entity.Player;
import de.atlasmc.inventory.Inventory;

public class InventoryCloseEvent extends InventoryEvent {

	public InventoryCloseEvent(Inventory inv) {
		super(inv);
	}

	public Player getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

}
