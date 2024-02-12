package de.atlasmc.event.inventory;

import de.atlasmc.entity.Player;
import de.atlasmc.event.Cancellable;
import de.atlasmc.inventory.InventoryView;

public abstract class InventoryInteractEvent extends InventoryEvent implements Cancellable {

	private boolean cancelled;
	
	public InventoryInteractEvent(InventoryView view) {
		super(view);
		cancelled = false;
	}
	
	public Player getWhoClicked() {
		return view.getPlayer();
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

}
