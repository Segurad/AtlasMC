package de.atlasmc.recipe;

import de.atlasmc.event.inventory.InventoryClickEvent;

public interface ResultProcessor {
	
	void process(InventoryClickEvent event);

}
