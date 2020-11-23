package de.atlasmc.event.inventory;

import de.atlasmc.entity.Player;
import de.atlasmc.event.Cancellable;
import de.atlasmc.inventory.Inventory;

public class InventoryOpenEvent extends InventoryEvent implements Cancellable {

	public InventoryOpenEvent(Inventory inv) {
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

	public Player getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

}
