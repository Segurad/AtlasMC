package de.atlascore.system.listener;

import de.atlasmc.entity.Player;
import de.atlasmc.event.EventHandler;
import de.atlasmc.event.EventPriority;
import de.atlasmc.event.Listener;
import de.atlasmc.event.inventory.InventoryClickEvent;
import de.atlasmc.event.inventory.InventoryCloseEvent;
import de.atlasmc.event.inventory.InventoryOpenEvent;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.gui.GUI;
import de.atlasmc.inventory.gui.button.Button;

final class GUIBListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onOpen(InventoryOpenEvent e) {
		Inventory inv = e.getInventory();
		GUI gui = (GUI) inv;
		if (gui == null) return;
		if (e.getPlayer() instanceof Player) gui.notifyOpenedBy((Player) e.getPlayer());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onClick(InventoryClickEvent e) {
		int slot = e.getSlot();
		if (slot < 0)
			return;
		Inventory inv = e.getClickedInventory();
		GUI gui = (GUI) inv;
		if (gui == null) return;
		if (!gui.isClickable(slot)) {
			e.setCancelled(true);
		}
		gui.notifyClick(e);
		Button button = gui.getButton(e.getSlot());
		if (button != null) {
			Player p = (Player) e.getWhoClicked();
			if (button.hasPermission()) {
				if (!p.hasPermission(button.getPermission())) {
					button.unauthorizedClick(p);
					return;
				}
			}
			ItemStack icon = button.press(e);
			if (icon == null) return; 
			if (icon.equals(inv.getItem(slot))) return; 
			inv.setItem(slot, icon);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onClose(InventoryCloseEvent e) {
		Inventory inv = e.getInventory();
		GUI gui = (GUI) inv;
		if (gui == null) return;
		if (e.getPlayer() instanceof Player) gui.notifyClosedBy((Player) e.getPlayer());
	}
}
