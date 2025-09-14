package de.atlasmc.node.event.inventory;

import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.ServerHandlerList;
import de.atlasmc.node.inventory.InventoryView;

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
