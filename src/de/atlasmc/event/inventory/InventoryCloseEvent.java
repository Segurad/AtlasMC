package de.atlasmc.event.inventory;

import de.atlasmc.entity.Player;
import de.atlasmc.event.HandlerList;
import de.atlasmc.inventory.InventoryView;

public class InventoryCloseEvent extends InventoryEvent {

	public InventoryCloseEvent(InventoryView view) {
		super(view);
	}

	public Player getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return null;
	}

}
