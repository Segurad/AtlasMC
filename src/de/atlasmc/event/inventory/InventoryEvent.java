package de.atlasmc.event.inventory;

import java.util.List;

import de.atlasmc.entity.Player;
import de.atlasmc.event.AbstractServerEvent;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryView;

public abstract class InventoryEvent extends AbstractServerEvent {
	
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

}
