package de.atlascore.inventory;

import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.LlamaInventory;

public class CoreLlamaInventory extends CoreInventory implements LlamaInventory {

	public CoreLlamaInventory(String title, InventoryHolder holder) {
		super(2, InventoryType.LLAMA, title, holder);
	}

}
