package de.atlascore.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.LlamaInventory;

public class CoreLlamaInventory extends CoreInventory implements LlamaInventory {

	public CoreLlamaInventory(Chat title, InventoryHolder holder) {
		super(2, InventoryType.LLAMA, title, holder);
	}

}
