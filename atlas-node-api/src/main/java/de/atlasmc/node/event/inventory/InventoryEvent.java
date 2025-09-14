package de.atlasmc.node.event.inventory;

import java.util.List;

import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.AbstractServerEvent;
import de.atlasmc.node.inventory.Inventory;
import de.atlasmc.node.inventory.InventoryView;

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
