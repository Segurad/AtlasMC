package de.atlasmc.node.event.inventory;

import java.util.Objects;

import de.atlasmc.node.event.AbstractServerEvent;
import de.atlasmc.node.inventory.Inventory;
import de.atlasmc.node.inventory.InventoryView;

public abstract class InventoryEvent extends AbstractServerEvent {
	
	protected final InventoryView view;
	protected final Inventory inv;
	
	public InventoryEvent(InventoryView view, Inventory inv) {
		super(view.getPlayer().getServer());
		this.view = view;
		this.inv = Objects.requireNonNull(inv, "inv");
	}
	
	public InventoryView getView() {
		return view;
	}
	
	public Inventory getInventory() {
		return inv;
	}

}
