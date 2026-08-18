package de.atlasmc.node.event.inventory;

import de.atlasmc.node.event.ServerHandlerList;
import de.atlasmc.node.inventory.Inventory;
import de.atlasmc.node.inventory.InventoryView;

public class InventoryChangeSlotStateEvent extends InventoryEvent {

	private static final ServerHandlerList HANDLERS = new ServerHandlerList();

	private final int slot;
	private final boolean enabled;
	
	public InventoryChangeSlotStateEvent(InventoryView view, Inventory inv, int slot, boolean enabled) {
		super(view, inv);
		this.slot = slot;
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public int getSlot() {
		return slot;
	}
	
	public static ServerHandlerList getHandlerList() {
		return HANDLERS;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return HANDLERS;
	}

}
