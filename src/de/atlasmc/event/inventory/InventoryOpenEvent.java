package de.atlasmc.event.inventory;

import de.atlasmc.event.Cancellable;
import de.atlasmc.event.HandlerList;
import de.atlasmc.inventory.InventoryView;

public class InventoryOpenEvent extends InventoryEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	

	public InventoryOpenEvent(InventoryView view) {
		super(view);
		cancelled = false;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
