package de.atlasmc.event.inventory;

import de.atlasmc.entity.Merchant;
import de.atlasmc.event.ServerHandlerList;
import de.atlasmc.inventory.InventoryView;

public class SelectTradeEvent extends InventoryInteractEvent {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private final int tradeID;
	
	public SelectTradeEvent(InventoryView view, int tradeID) {
		super(view);
		this.tradeID = tradeID;
	}
	
	public int getTradeID() {
		return tradeID;
	}
	
	public Merchant getMerchant() {
		// TODO
		return null;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

}
