package de.atlasmc.event.inventory;

import de.atlasmc.event.Cancellable;
import de.atlasmc.inventory.Inventory;

public class InventoryInteractEvent extends InventoryEvent implements Cancellable {

	public InventoryInteractEvent(Inventory inv) {
		super(inv);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setCancelled(boolean cancelled) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

}
