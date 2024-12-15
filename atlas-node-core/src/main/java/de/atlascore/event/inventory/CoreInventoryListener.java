package de.atlascore.event.inventory;

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

public final class CoreInventoryListener implements Listener {
	
	@EventHandler
	public void onOpen(InventoryOpenEvent e) {
		if (e.isCancelled())
			return;
		Inventory inv = e.getInventory();
		GUI gui = inv.getGUI();
		if (gui == null) 
			return;
		gui.notifyOpenedBy(e.getPlayer());
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		final Inventory inv = e.getClickedInventory();
		final int slot = e.getSlot();
		final Player player = e.getWhoClicked();
		final GUI gui = inv.getGUI();
		if (gui != null) {
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
			inv.updateSlots(player);
			return;
		}
		// Default event handling
		if (slot == -999) 
			return;
		// TODO update inv
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		final Inventory inv = e.getInventory();
		final Player player = e.getPlayer();
		final GUI gui = inv.getGUI();
		if (gui != null) {
			gui.notifyClosedBy(player);
		}
		player.getConnection().setSelectedTrade(-1);
		// nothing to process cause inventory will be closed no matter what
	}
	
}
