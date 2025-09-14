package de.atlasmc.node.event.inventory;

import de.atlasmc.node.entity.Merchant;
import de.atlasmc.node.event.ServerHandlerList;
import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.InventoryView;

public class SelectTradeEvent extends InventoryInteractEvent {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private final int oldTradeID;
	private final int tradeID;
	
	public SelectTradeEvent(InventoryView view, int tradeID, int oldTradeID) {
		super(view);
		this.tradeID = tradeID;
		this.oldTradeID = oldTradeID;
	}

	/**
	 * Returns the selected trade index or -1 if none.
	 * @return index or -1
	 */
	public int getOldTradeID() {
		return oldTradeID;
	}
	
	public int getTradeID() {
		return tradeID;
	}
	
	/**
	 * 
	 * @return a Merchant or null
	 */
	public Merchant getMerchant() {
		InventoryHolder h = view.getTopInventory().getHolder();
		if (h == null || !(h instanceof Merchant merchant)) 
			return null;
		return merchant;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

}
