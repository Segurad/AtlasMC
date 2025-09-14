package de.atlasmc.node.recipe;

import de.atlasmc.node.event.inventory.InventoryClickEvent;

public interface ResultProcessor {
	
	void process(InventoryClickEvent event);

}
