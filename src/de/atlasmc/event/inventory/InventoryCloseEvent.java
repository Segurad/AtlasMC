package de.atlasmc.event.inventory;

import de.atlasmc.entity.Player;
import de.atlasmc.event.ServerHandlerList;
import de.atlasmc.inventory.InventoryView;

/**
 * Fired when a {@link Player#closeInventory()} is called
 */
public class InventoryCloseEvent extends InventoryEvent {

	protected static final ServerHandlerList handlers = new ServerHandlerList();
	
	public InventoryCloseEvent(InventoryView view) {
		super(view);
	}

	public Player getPlayer() {
		return view.getPlayer();
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

}
