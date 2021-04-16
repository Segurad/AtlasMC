package de.atlasmc.event.inventory;

import java.util.List;

import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.entity.Player;
import de.atlasmc.event.GenericEvent;
import de.atlasmc.event.ServerHandlerList;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryView;

public abstract class InventoryEvent extends GenericEvent<LocalServer, ServerHandlerList> {
	
	protected InventoryView view;
	
	public InventoryEvent(InventoryView view) {
		super(view.getPlayer().getServer());
		this.view = view;
	}
	
	public InventoryView getView() {
		return view;
	}
	
	public Inventory getInventory() {
		return view.getTopInventory();
	}
	
	public List<Player> getViewers() {
		return view.getTopInventory().getViewers();
	}
	
	public Player getPlayer() {
		return view.getPlayer();
	}

}
