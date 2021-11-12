package de.atlascore.system.listener;

import de.atlasmc.entity.Player;
import de.atlasmc.event.EventHandler;
import de.atlasmc.event.Listener;
import de.atlasmc.event.inventory.InventoryClickEvent;
import de.atlasmc.event.inventory.InventoryCloseEvent;
import de.atlasmc.event.inventory.InventoryOpenEvent;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.gui.GUI;
import de.atlasmc.inventory.gui.button.Button;

final class InventoryListener implements Listener {
	
	@EventHandler
	public void onOpen(InventoryOpenEvent e) {
		Inventory inv = e.getInventory();
		GUI gui = (GUI) inv;
		if (gui == null) return;
		gui.notifyOpenedBy(e.getPlayer());
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		final int slot = e.getSlot();
		final Inventory inv = e.getClickedInventory();
		final Player player = e.getWhoClicked();
		if (inv instanceof GUI) {
			GUI gui = (GUI) inv;
			if (!gui.isClickable(slot)) {
				e.setCancelled(true);
			}
			gui.notifyClick(e);
			Button button = gui.getButton(e.getSlot());
			if (button != null) {
				if (button.hasPermission()) {
					if (!player.hasPermission(button.getPermission())) {
						button.unauthorizedClick(player);
					}
				} else {
					ItemStack icon = button.press(e);
					if (icon != null && !icon.equals(inv.getItem(slot))) 
						inv.setItem(slot, icon);
				}
			}
		}
		// Processing event results
		if (e.isCancelled()) { // cancelled event
			player.getConnection().setWindowLock();
			inv.updateSlot(player, slot, false);
			return;
		}
		// Default event handling
		if (slot == -999) return;
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		final Inventory inv = e.getInventory();
		final Player player = e.getPlayer();
		if (inv instanceof GUI) {
			GUI gui = (GUI) inv;
			gui.notifyClosedBy(player);
		}
		// nothing to process cause inventory will be closed no matter what
	}
}
