package de.atlasmc.node.event.inventory;

import de.atlasmc.node.event.ServerHandlerList;
import de.atlasmc.node.inventory.InventoryView;

public class InventoryClickButtonEvent extends InventoryInteractEvent {
	
	private static final ServerHandlerList handlers = new ServerHandlerList();
	private final InventoryButtonType type;
	private final int value;

	/**
	 * 
	 * @param view
	 * @param type
	 * @param value a extra number like the page number if the type is {@link InventoryButtonType#LECTERN_OPEN_PAGE_NUMBER} or -1 if not necessary 
	 */
	public InventoryClickButtonEvent(InventoryView view, InventoryButtonType type, int value) {
		super(view);
		this.type = type;
		this.value = value;
	}
	
	public InventoryButtonType getButtonType() {
		return type;
	}
	
	public int getValue() {
		return value;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

}
