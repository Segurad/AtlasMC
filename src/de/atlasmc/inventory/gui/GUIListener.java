package de.atlasmc.inventory.gui;

import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryClickEvent;

public interface GUIListener {

	public void openedBy(Player player);
	public void closedBy(Player player);
	public void click(InventoryClickEvent event);

}
