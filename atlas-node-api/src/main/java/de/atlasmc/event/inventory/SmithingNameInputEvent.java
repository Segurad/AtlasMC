package de.atlasmc.event.inventory;

import de.atlasmc.event.ServerHandlerList;
import de.atlasmc.inventory.InventoryView;

public class SmithingNameInputEvent extends InventoryEvent {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private final String name;
	
	public SmithingNameInputEvent(InventoryView view, String name) {
		super(view);
		this.name = name;
	}
	
	public String getInputName() {
		return name;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

}
