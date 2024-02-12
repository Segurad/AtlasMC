package de.atlasmc.event.inventory;

import de.atlasmc.event.ServerHandlerList;
import de.atlasmc.inventory.InventoryView;
import de.atlasmc.inventory.ItemStack;

public class InventoryCreativeClickEvent extends InventoryClickEvent {
	
	private static final ServerHandlerList handlers = new ServerHandlerList();

	private ItemStack newItem;
	
	public InventoryCreativeClickEvent(InventoryView view, int rawSlot, ItemStack item) {
		super(view, rawSlot, ClickType.CREATIV, InventoryAction.PLACE_ALL);
		this.newItem = item;
	}
	
	@Override
	public ItemStack getCurrentItem() {
		return newItem;
	}
	
	@Override
	public void setCurrentItem(ItemStack item) {
		this.newItem = item;
	}
	
	@Override
	public ItemStack getCursor() {
		return newItem;
	}
	
	@Override
	public void setCursor(ItemStack item) {
		this.newItem = item;
	}
	
	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

}
