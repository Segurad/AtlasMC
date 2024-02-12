package de.atlasmc.event.inventory;

import de.atlasmc.event.ServerHandlerList;
import de.atlasmc.inventory.InventoryView;
import de.atlasmc.inventory.ItemStack;

public class PrepareSmithingEvent extends InventoryEvent {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private ItemStack result;
	
	public PrepareSmithingEvent(InventoryView view, ItemStack result) {
		super(view);
		this.result = result;
	}
	
	public ItemStack getResult() {
		return result;
	}
	
	public void setResult(ItemStack result) {
		this.result = result;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

}
