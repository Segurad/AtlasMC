package de.atlasmc.event.inventory;

import de.atlasmc.entity.Player;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryView;

public class InventoryOpenEvent extends InventoryEvent implements Cancellable {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	private boolean cancelled;
	private Inventory inv;

	public InventoryOpenEvent(InventoryView view, Inventory inv) {
		super(view);
		cancelled = false;
		this.inv = inv;
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
	public Inventory getInventory() {
		return inv;
	}
	
	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

	public Player getPlayer() {
		return getView().getPlayer();
	}

}
