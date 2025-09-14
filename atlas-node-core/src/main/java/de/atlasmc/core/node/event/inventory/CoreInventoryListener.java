package de.atlasmc.core.node.event.inventory;

import de.atlasmc.event.EventHandler;
import de.atlasmc.event.Listener;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.inventory.InventoryClickEvent;
import de.atlasmc.node.event.inventory.InventoryCloseEvent;
import de.atlasmc.node.inventory.Inventory;

public final class CoreInventoryListener implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		final Inventory inv = e.getClickedInventory();
		final int slot = e.getSlot();
		final Player player = e.getWhoClicked();
		// Processing event results
		if (e.isCancelled()) { // cancelled event
			inv.updateSlots(player);
			return;
		}
		// Default event handling
		if (slot == -999) 
			return;
		// TODO update inv
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {;
		final Player player = e.getPlayer();
		player.getConnection().setSelectedTrade(-1);
		// nothing to process cause inventory will be closed no matter what
	}
	
}
