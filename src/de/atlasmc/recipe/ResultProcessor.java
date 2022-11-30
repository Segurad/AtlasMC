package de.atlasmc.recipe;

import de.atlasmc.event.inventory.InventoryClickEvent;

public interface ResultProcessor {
	
	public void process(InventoryClickEvent event);

}
